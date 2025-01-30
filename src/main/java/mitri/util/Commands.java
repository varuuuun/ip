package mitri.util;

public enum Commands {
    LIST, BYE, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND;

    public static Commands getCommand(String command) {
        return Commands.valueOf(command.toUpperCase());
    }
}
