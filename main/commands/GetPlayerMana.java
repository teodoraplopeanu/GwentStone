package main.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.Game;

public final class GetPlayerMana extends Command {
    public GetPlayerMana() {
        super("getPlayerMana");
    }

    @Override
    public void executeCommand(final ActionsInput actionsInput, final Game game,
                               final ObjectNode actionResult) {
        actionResult.put("command", getCommand());
        int playerIdx = actionsInput.getPlayerIdx();
        actionResult.put("playerIdx", playerIdx);
        actionResult.put("output", game.getPlayer(playerIdx).getMana());
    }
}
