package main.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.Game;

public final class GetCardAtPosition extends Command {
    public GetCardAtPosition() {
        super("getCardAtPosition");
    }

    @Override
    public void executeCommand(final ActionsInput actionsInput, final Game game,
                               final ObjectNode actionResult) {
        int row = actionsInput.getX();
        int col = actionsInput.getY();

        actionResult.put("command", getCommand());
        actionResult.put("x", row);
        actionResult.put("y", col);

        if (col >= game.getGameBoard().getGameBoard().get(row).size()) {
            actionResult.put("output", "No card available at that position.");
            return;
        }

        ObjectNode card = game.getGameBoard().getCardAt(row, col).createObjectNode();
        actionResult.put("output", card);
    }
}
