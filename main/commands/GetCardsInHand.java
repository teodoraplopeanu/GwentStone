package main.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.Game;
import main.Player;

public final class GetCardsInHand extends Command {
    public GetCardsInHand() {
        super("getCardsInHand");
    }

    @Override
    public void executeCommand(final ActionsInput actionsInput, final Game game,
                               final ObjectNode actionResult) {
        actionResult.put("command", getCommand());
        int playerIdx = actionsInput.getPlayerIdx();
        actionResult.put("playerIdx", playerIdx);

        Player player = game.getPlayer(playerIdx);
        ArrayNode hand = player.getHand().createArrayNode();
        actionResult.put("output", hand);
    }
}
