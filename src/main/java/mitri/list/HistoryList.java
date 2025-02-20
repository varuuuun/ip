package mitri.list;

import java.util.ArrayList;

/**
 * Represents Task list.
 */
public class HistoryList extends BasicList<String> {
    private ArrayList<String> commands;

    /**
     * Initialises history list.
     */
    public HistoryList() {
        commands = new ArrayList<String>();
    }

    /**
     * Removes command at top of list.
     *
     * @return Removed command.
     */
    public String remove() throws IllegalArgumentException{
        if (commands.isEmpty()) {
            throw new IllegalArgumentException("There is no command to undo");
        }
        return commands.remove(0);
    }

    /**
     * Adds command to list.
     *
     * @param command command add to list.
     */
    public void add(String command) {
        commands.add(0, command);
    }

    public void addFromFile(String command) {
        commands.add(command);
    }

    /**
     * Returns current size of list.
     *
     * @return Size of list.
     */
    public int size() {
        return commands.size();
    }

    public String get(int index) {
        return commands.get(index);
    }
}
