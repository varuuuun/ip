package mitri.chatbot;

import static java.lang.Math.min;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import mitri.task.Deadline;
import mitri.task.Event;
import mitri.task.Task;
import mitri.task.Todo;
import mitri.ui.Ui;
import mitri.util.Parser;
import mitri.util.Storage;
import mitri.util.TaskList;


/**
 * Represents the chatbot Mitri, handling user interaction and functionality.
 */
public class Mitri {
    private String botName;
    private String logo;
    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private Parser parser;

    /**
     * Constructs a Mitri chatbot instance.
     * Initializes necessary components for user interaction and functionality.
     */
    public Mitri() {
        this.botName = "Mitri";
        ui = new Ui();
        taskList = new TaskList();
        parser = new Parser(this, ui);
        storage = new Storage(parser, ui, taskList);
    }

    /**
     * Starts the chatbot and begins processing user commands.
     */
    public void run() {
        storage.loadFromFile();
        greet();
        int running = 1;

        while (running == 1) {
            String input = ui.getInput();
            running = parser.processInput(input);
        }

        exit();
    }

    /**
     * Echoes input, originally used to test chatbot. Currently unused.
     *
     * @param str String to echo.
     */
    private void echo(String str) {
        ui.print(str);
    }

    /**
     * Deletes item at given index & reports to user.
     *
     * @param index of item to delete from task list.
     */
    public void delete(int index) {
        Task t = taskList.remove(index);
        ui.print("Got it. I've removed this task:\n\t" + t + "\nNow you have " + taskList.size()
                + " tasks in the list.");
    }

    /**
     * Creates new Todo task and calls function to add it to task list.
     *
     * @param str String containing "description" field of Todo task.
     * @throws IllegalArgumentException Thrown when str is blank.
     */
    public void addTodo(String str) throws IllegalArgumentException {
        if (str.isBlank()) {
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }

        add(new Todo(str.stripLeading()));
    }

    /**
     * Creates new Deadline task and calls function to add it to task list.
     *
     * @param str String containing "description"and "by" fields of Deadline task.
     * @throws IllegalArgumentException Thrown when "description" or "by" field is blank.
     * @throws DateTimeParseException Thrown when "by" field cannot be parsed as a LocalDateTime object.
     */
    public void addDeadline(String str) throws IllegalArgumentException, DateTimeParseException {
        String[] parts = str.split(" /by ");

        if (parts[0].isBlank() || parts.length == 1 || parts[1].isBlank()) {
            throw new IllegalArgumentException("Deadline missing one or more fields. \n"
                    + "Ensure you provide description and by fields.");
        }

        add(new Deadline(parts[0].stripLeading(), extractDateTime(parts[1])));
    }

    /**
     * Creates new Event task and calls function to add it to task list.
     * "from" and "to" fields can be in any orer.
     *
     * @param str String containing "description", "from" and "to" fields of Event task.
     * @throws IllegalArgumentException Thrown when one or more of the required fields are blank.
     * @throws DateTimeParseException Thrown when "from" or "to" fields cannot be parsed as LocalDateTime objects.
     */
    public void addEvent(String str) throws IllegalArgumentException, DateTimeParseException {
        int from = str.indexOf(" /from ");
        int to = str.indexOf(" /to ");

        if ((from <= 0) || (to <= 0)) {
            throw new IllegalArgumentException("Event missing one or more fields. \n"
                    + "Ensure you provide description, from and to fields.");
        }

        String descStr = str.substring(0, min(from, to));
        String fromStr, toStr;

        if (from < to) {
            fromStr = str.substring(from + 7, to);
            toStr = str.substring(to + 5);
        } else {
            fromStr = str.substring(from + 7);
            toStr = str.substring(to + 5, from);
        }

        if (descStr.isBlank() || fromStr.isBlank() || toStr.isBlank()) {
            throw new IllegalArgumentException("Event has one or more empty fields. "
                    + "Ensure you provide description, from and to fields.");
        }

        add(new Event(descStr.stripLeading(), extractDateTime(fromStr), extractDateTime(toStr)));
    }

    /**
     * Parses input string into a LocalDateTime object wherever possible.
     *
     * @param str String to parse to LocalDateTime object.
     * @return LocalDateTime object created from given string.
     * @throws DateTimeParseException Thrown when string cannot be parsed into a LocalDateTime object.
     */
    private LocalDateTime extractDateTime(String str) throws DateTimeParseException {
        if (str.indexOf('T') > 0) {
            return LocalDateTime.parse(str);
        } else if (str.indexOf('-') > 0) {
            return LocalDate.parse(str).atStartOfDay();
        } else {
            return LocalDateTime.of(LocalDate.now(), LocalTime.parse(str));
        }
    }

    /**
     * Adds given task to task list.
     *
     * @param t Task to add to list.
     */
    public void add(Task t) {
        taskList.add(t);
        ui.print("Got it. I've added this task:\n\t" + t
                + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    /**
     * Marks given task as done.
     *
     * @param i Index of task to mark as done.
     * @throws IndexOutOfBoundsException Thrown when given index is out of the range of the task list.
     */
    public void mark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setDone();
        ui.print("Nice! I've marked this task as done:\n\t" + t);
    }

    /**
     * Marks given task as not done.
     *
     * @param i Index of task to mark as not done.
     * @throws IndexOutOfBoundsException Thrown when given index is out of the range of the task list.
     */
    public void unmark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setNotDone();
        ui.print("OK, I've marked this task as not done yet:\n\t" + t);
    }

    /**
     * Lists all tasks in task list.
     */
    public void list() {
        String printString = "Here are the tasks in your list:" + taskList.toString();
        ui.print(printString);
    }


    /**
     * Finds and returns a list of all tasks whose description contains the given string.
     *
     * @param str String to search in description of task.
     */
    public void find(String str) {
        String printString = "Here are the matching tasks in your list:" + taskList.find(str).toString();
        ui.print(printString);
    }

    /**
     * Greets user.
     */
    private void greet() {
        ui.print("Hello! I'm " + botName + "\n" + "What can I do for you?");
    }

    /**
     * Cleans up and exits.
     */
    private void exit() {
        ui.closeScanner();
        storage.writeToFile();
        ui.print("Bye. Hope to see you again soon!");
    }

}