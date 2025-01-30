package mitri.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import mitri.chatbot.Mitri;
import mitri.task.Deadline;
import mitri.task.Event;
import mitri.task.Task;
import mitri.task.Todo;
import mitri.ui.Ui;

/**
 * Parser to retrieve commands from input and call relevant functions.
 */
public class Parser {
    private Mitri mitri;
    private Ui ui;

    /**
     * Initialise Parser
     *
     * @param mitri Chatbot
     * @param ui Ui for printing error messages
     */
    public Parser(Mitri mitri, Ui ui) {
        this.mitri = mitri;
        this.ui = ui;
    }


    /**
     * Given line from file, parses and returns task corresponding to given line.
     *
     * @param input String to parse into Task.
     * @return Task parsed from input.
     * @throws DateTimeParseException Thrown if DateTime field cannot be parsed properly.
     * @throws IllegalArgumentException Thrown if line contains errors that show file is corrupted.
     */
    public Task parseTaskFromFile(String input) throws DateTimeParseException, IllegalArgumentException {
        String[] parts = input.split(" \\| ");
        Task task;

        switch (parts[0]) {
        case "D":
            task = new Deadline(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length - 1)),
                    LocalDateTime.parse(parts[parts.length - 1]));
            break;
        case "E":
            task = new Event(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length - 2)),
                    LocalDateTime.parse(parts[parts.length - 2]), LocalDateTime.parse(parts[parts.length - 1]));
            break;
        default:
            task = new Todo(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length)));
            break;
        }

        if (parts[1].equals("1")) {
            task.setDone();
        } else {
            task.setNotDone();
        }

        return task;
    }


    /**
     * Parses given command line input into its relevant command and executes it.
     *
     * @param input String with command to execute.
     * @return 0 if bye received (exit), 1 to continue running chatbot.
     */
    public int processInput(String input) {
        String[] parts = input.split(" ");

        if (input.equals("bye")) {
            return 0;
        }

        Commands command;
        try {
            command = Commands.getCommand(parts[0]);
        } catch (IllegalArgumentException e) {
            ui.printError("I'm sorry, but I don't know what that means :(");
            return 1;
        }

        try {
            switch (command) {
            case LIST:
                mitri.list();
                break;
            case BYE:
                return 0;
            case DELETE:
                mitri.delete(Integer.parseInt(parts[1]) - 1);
                break;
            case MARK:
                mitri.mark(Integer.parseInt(parts[1]) - 1);
                break;
            case UNMARK:
                mitri.unmark(Integer.parseInt(parts[1]) - 1);
                break;
            case TODO:
                mitri.addTodo(input.substring(4));
                break;
            case DEADLINE:
                mitri.addDeadline(input.substring(8));
                break;
            case EVENT:
                mitri.addEvent(input.substring(5));
                break;
            }
        } catch (DateTimeParseException e) {
            ui.printError("You did not provide a readable date/time");
        } catch (NumberFormatException e) {
            ui.printError("Not a number. Please give the index of the task!");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("Index out of bounds. Please give the correct index!");
        } catch (IllegalArgumentException e) {
            ui.printError(e.getMessage());
        }

        return 1;
    }


}
