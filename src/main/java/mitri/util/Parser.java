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
 * Represents Parser to retrieve commands from input and call relevant functions.
 */
public class Parser {
    private Mitri mitri;

    /**
     * Initialises Parser
     *
     * @param mitri Chatbot
     */
    public Parser(Mitri mitri) {
        this.mitri = mitri;
    }


    /**
     * Parses and returns task corresponding to given line of file.
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
     * @return String to output to user.
     */
    public String processInput(String input) {
        String[] parts = input.split(" ");

        Commands command;
        try {
            command = Commands.getCommand(parts[0]);
        } catch (IllegalArgumentException e) {
            command = Commands.DEFAULT;
        }

        try {
            switch (command) {
            case LIST:
                return mitri.list();
            case BYE:
                return mitri.exit();
            case DELETE:
                return mitri.delete(Integer.parseInt(parts[1]) - 1);
            case MARK:
                return mitri.mark(Integer.parseInt(parts[1]) - 1);
            case UNMARK:
                return mitri.unmark(Integer.parseInt(parts[1]) - 1);
            case TODO:
                return mitri.addTodo(input.substring(4));
            case DEADLINE:
                return mitri.addDeadline(input.substring(8));
            case EVENT:
                return mitri.addEvent(input.substring(5));
            case FIND:
                return mitri.find(parts[1]);
            default:
                return mitri.showError("I'm sorry, but I don't know what that means :(");
            }
        } catch (DateTimeParseException e) {
            return mitri.showError("You did not provide a readable date/time");
        } catch (NumberFormatException e) {
            return mitri.showError("Not a number. Please give the index of the task!");
        } catch (IndexOutOfBoundsException e) {
            return mitri.showError("Index out of bounds. Please give the correct index!");
        } catch (IllegalArgumentException error) {
            return mitri.showError(error.getMessage());
        }
    }


}
