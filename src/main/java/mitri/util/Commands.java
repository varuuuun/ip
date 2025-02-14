package mitri.util;

/**
 * Represents for all possible commands.
 */
public enum Commands {
    LIST, BYE, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNDO, DEFAULT;

    /**
     * Gets command from given string.
     *
     * @param command String with command
     * @return Command
     */
    public static Commands getCommand(String command) {
        assert command != null: "Command string should be null";
        assert !command.isBlank() : "Command string should not be empty";
        return Commands.valueOf(command.toUpperCase());
    }
}
