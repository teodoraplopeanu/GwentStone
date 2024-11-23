package main.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.Game;
import main.Player;

public final class GetPlayerDeck extends Command {
    public GetPlayerDeck() {
        super("getPlayerDeck");
    }

    @Override
    public void executeCommand(final ActionsInput actionsInput, final Game game,
                               final ObjectNode actionResult) {
        actionResult.put("command", getCommand());
        int playerIdx = actionsInput.getPlayerIdx();
        actionResult.put("playerIdx", playerIdx);

        Player player = game.getPlayer(playerIdx);
        ArrayNode playerDeck = player.getDeck().createArrayNode();

        actionResult.put("output", playerDeck);
    }
}
