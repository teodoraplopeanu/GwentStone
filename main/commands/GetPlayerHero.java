package main.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.Game;
import main.Player;

public final class GetPlayerHero extends Command {
    public GetPlayerHero() {
        super("getPlayerHero");
    }

    @Override
    public void executeCommand(final ActionsInput actionsInput, final Game game,
                               final ObjectNode actionResult) {
        actionResult.put("command", getCommand());
        int playerIdx = actionsInput.getPlayerIdx();
        actionResult.put("playerIdx", playerIdx);

        Player player = game.getPlayer(playerIdx);
        ObjectNode playerHero = player.getHero().createObjectNode();

        actionResult.put("output", playerHero);
    }
}
