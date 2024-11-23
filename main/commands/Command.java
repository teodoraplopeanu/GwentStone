package main.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.Game;

public class Command {
    protected String command;

    public Command(final String command) {
        this.command = command;
    }

    /**
     * Executes the requested command
     * */
    public void executeCommand(final ActionsInput actionsInput, final Game game,
                               final ObjectNode actionResult) { }

    public final String getCommand() {
        return command; }

    public final void setCommand(final String command) {
        this.command = command;
    }
}
