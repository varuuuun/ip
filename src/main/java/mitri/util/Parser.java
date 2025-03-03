package mitri.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import mitri.chatbot.Mitri;
import mitri.task.Deadline;
import mitri.task.Event;
import mitri.task.Task;
import mitri.task.Todo;

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
        case "T":
            task = new Todo(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length)));
            break;
        default:
            throw new IllegalArgumentException("Invalid task content");
        }

        if (parts[1].equals("1")) {
            task.setDone();
        } else if (parts[1].equals("0")) {
            task.setNotDone();
        } else {
            throw new IllegalArgumentException("Invalid completion value format");
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
        assert input != null : "Input string for parsing should not be null";
        assert !input.trim().isBlank() : "Input string for parsing should not be empty";
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
                mitri.writeHistory(input);
                return mitri.delete(Integer.parseInt(parts[1]) - 1);
            case MARK:
                mitri.writeHistory(input);
                return mitri.mark(Integer.parseInt(parts[1]) - 1);
            case UNMARK:
                mitri.writeHistory(input);
                return mitri.unmark(Integer.parseInt(parts[1]) - 1);
            case TODO:
                mitri.writeHistory(input);
                return mitri.addTodo(input.substring(4));
            case DEADLINE:
                mitri.writeHistory(input);
                return mitri.addDeadline(input.substring(8));
            case EVENT:
                mitri.writeHistory(input);
                return mitri.addEvent(input.substring(5));
            case FIND:
                return mitri.find(parts[1]);
            case UNDO:
                return mitri.undo();
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

    public String parseUndo(String input) {
        assert input != null : "Input string for undo should not be null";
        assert !input.trim().isBlank() : "Input string for undo should not be empty";
        switch (input) {
        case "todo", "deadline", "event":
            return "delete";
        case "mark":
            return "unmark";
        case "unmark":
            return "mark";
        case "delete":
            return "add";
        default:
            return mitri.showError("History file is corrupted");
        }
    }


}
