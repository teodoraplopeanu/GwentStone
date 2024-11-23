package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        Statistics statistics = new Statistics();
        for (int gameIdx = 0; gameIdx < inputData.getGames().size(); gameIdx++) {
            Game game = new Game(inputData, gameIdx, statistics);

            for (ActionsInput actionsInput : inputData.getGames().get(gameIdx).getActions()) {
                ObjectNode actionResult = game.handleAction(actionsInput);

                if (game.isGameOver()) {
                    ObjectNode winner = objectMapper.createObjectNode();
                    if (game.getWinner() == 1) {
                        winner.put("gameEnded", "Player one killed the enemy hero.");
                        statistics.setPlayerOneWins(statistics.getPlayerOneWins() + 1);
                    } else {
                        winner.put("gameEnded", "Player two killed the enemy hero.");
                        statistics.setPlayerTwoWins(statistics.getPlayerTwoWins() + 1);
                    }
                    output.add(winner);
                    game.setGameOver(false);
                    statistics.setTotalGamesPlayed(statistics.getTotalGamesPlayed() + 1);
                    continue;
                }

                if (actionResult != null) {
                    output.add(actionResult);
                }
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
