package mitri.util;

/**
 * Represents for all possible commands.
 */
public enum Commands {
    LIST, BYE, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND;

    /**
     * Gets command from given string.
     *
     * @param command String with command
     * @return Command
     */
    public static Commands getCommand(String command) {
        return Commands.valueOf(command.toUpperCase());
    }
}
