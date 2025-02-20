package mitri.chatbot;

import static java.lang.Math.min;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;

import mitri.task.Deadline;
import mitri.task.Event;
import mitri.task.Task;
import mitri.task.Todo;
import mitri.ui.Gui;
import mitri.util.Parser;
import mitri.util.Storage;
import mitri.list.TaskList;
import mitri.list.HistoryList;
import mitri.list.DeletedTasksList;


/**
 * Represents the chatbot Mitri, handling user interaction and functionality.
 */
public class Mitri extends Application {
    private String botName;
    private Gui ui;
    private TaskList taskList;
    private DeletedTasksList deletedTasksList;
    private HistoryList historyList;
    private Storage storage;
    private Parser parser;

    /**
     * Constructs a Mitri chatbot instance.
     * Initializes necessary components for user interaction and functionality.
     */
    public Mitri() {
        this.botName = "Mitri";
        taskList = new TaskList();
        deletedTasksList = new DeletedTasksList();
        historyList = new HistoryList();
        parser = new Parser(this);
        storage = new Storage(parser, this, taskList, deletedTasksList, historyList);
        ui = new Gui(parser);
    }

    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Mitri.class.getResource("/view/Gui.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<Gui>getController().setParser(parser);  // inject the Duke instance
            stage.setTitle("Mitri");
            stage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        storage.loadFromSaveFile();
        storage.loadFromHistoryFile();
        storage.loadFromDeletedTasksFile();
    }

    public String showError(String str){
        return ui.getError(str);
    }

    /**
     * Starts the chatbot and begins processing user commands.
     */
    public void run() {
        storage.loadFromSaveFile();
        greet();

        while (true) {
            String input = ui.getInput();
            parser.processInput(input);
        }
    }

    /**
     * Deletes item at given index & reports to user.
     *
     * @param index of item to delete from task list.
     * @return String to output to user.
     */
    public String delete(int index) {
        Task t = taskList.remove(index);
        storage.writeToSaveFile();
        deletedTasksList.add(t);
        storage.writeToDeletedTasksFile();
        return "Got it. I've removed this task:\n\t" + t.toOutputString() + "\n" + getLengthString();
    }

    /**
     * Creates new Todo task and calls function to add it to task list.
     *
     * @param str String containing "description" field of Todo task.
     * @return String to output to user.
     * @throws IllegalArgumentException Thrown when str is blank.
     */
    public String addTodo(String str) throws IllegalArgumentException {
        if (str.isBlank()) {
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }

        return add(new Todo(str.stripLeading()));
    }

    /**
     * Creates new Deadline task and calls function to add it to task list.
     *
     * @param str String containing "description"and "by" fields of Deadline task.
     * @return String to output to user.
     * @throws IllegalArgumentException Thrown when "description" or "by" field is blank.
     * @throws DateTimeParseException Thrown when "by" field cannot be parsed as a LocalDateTime object.
     */
    public String addDeadline(String str) throws IllegalArgumentException, DateTimeParseException {
        String[] parts = str.split(" /by ");

        if (parts[0].isBlank() || parts.length == 1 || parts[1].isBlank()) {
            throw new IllegalArgumentException("Deadline missing one or more fields. \n"
                    + "Ensure you provide description and by fields.");
        }

        return add(new Deadline(parts[0].stripLeading(), extractDateTime(parts[1])));
    }

    /**
     * Creates new Event task and calls function to add it to task list.
     * "from" and "to" fields can be in any orer.
     *
     * @param str String containing "description", "from" and "to" fields of Event task.
     * @return String to output to user.
     * @throws IllegalArgumentException Thrown when one or more of the required fields are blank.
     * @throws DateTimeParseException Thrown when "from" or "to" fields cannot be parsed as LocalDateTime objects.
     */
    public String addEvent(String str) throws IllegalArgumentException, DateTimeParseException {
        int from = str.indexOf(" /from ");
        int to = str.indexOf(" /to ");

        if ((from <= 0) || (to <= 0)) {
            throw new IllegalArgumentException("Event missing one or more fields. \n"
                    + "Ensure you provide description, from and to fields.");
        }

        String descStr = str.substring(0, min(from, to));
        String fromStr = extractFromString(str, from, to);
        String toStr = extractToString(str, from, to);

        if (descStr.isBlank() || fromStr.isBlank() || toStr.isBlank()) {
            throw new IllegalArgumentException("Event has one or more empty fields. "
                    + "Ensure you provide description, from and to fields.");
        }

        return add(new Event(descStr.stripLeading(), extractDateTime(fromStr), extractDateTime(toStr)));
    }

    private String extractFromString(String str, int from, int to) {
        if (from < to) {
            return str.substring(from + 7, to);
        } else {
            return str.substring(from + 7);
        }
    }

    private String extractToString(String str, int from, int to) {
        if (from < to) {
            return str.substring(to + 5);
        } else {
            return str.substring(to + 5, from);
        }
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
        } else if (str.indexOf(':') > 0) {
            return LocalDateTime.of(LocalDate.now(), LocalTime.parse(str));
        } else {
            throw new DateTimeParseException("Invalid date format.", str, 0);
        }
    }

    /**
     * Adds given task to task list.
     *
     * @param t Task to add to list.
     * @return String to output to user.
     */
    public String add(Task t) {
        taskList.add(t);
        storage.writeToSaveFile();
        return "Got it. I've added this task:\n\t" + t.toOutputString() + "\n" + getLengthString();
    }

    private String getLengthString() {
        return "Now you have " + taskList.size() + " tasks in the list.";
    }

    /**
     * Marks given task as done.
     *
     * @param i Index of task to mark as done.
     * @return String to output to user.
     * @throws IndexOutOfBoundsException Thrown when given index is out of the range of the task list.
     */
    public String mark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setDone();
        storage.writeToSaveFile();
        return "Nice! I've marked this task as done:\n\t" + t.toOutputString();
    }

    /**
     * Marks given task as not done.
     *
     * @param i Index of task to mark as not done.
     * @return String to output to user.
     * @throws IndexOutOfBoundsException Thrown when given index is out of the range of the task list.
     */
    public String unmark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setNotDone();
        storage.writeToSaveFile();
        return "OK, I've marked this task as not done yet:\n\t" + t.toOutputString();
    }

    /**
     * Lists all tasks in task list.
     * @return String to output to user.
     */
    public String list() {
        return "Here are the tasks in your list:" + taskList.toString();
    }

    /**
     * Finds and returns a list of all tasks whose description contains the given string.
     *
     * @param str String to search in description of task.
     * @return String to output to user.
     */
    public String find(String str) {
        return "Here are the matching tasks in your list:" + taskList.find(str).toString();
    }

    public void writeHistory(String command) {
        historyList.add(command);
        storage.writeToHistoryFile();
    }

    public String undo(){
        String command = historyList.remove();
        String[] partsOfCommand = command.split(" ");
        String action = parser.parseUndo(partsOfCommand[0]);
        doAction(action, partsOfCommand);
        storage.writeToDeletedTasksFile();
        return "Last change has been reverted!";
    }

    private void doAction(String action, String[] partsOfCommand) {
        switch (action) {
        case "delete":
            delete(taskList.size()-1);
            break;
        case "mark":
            mark(Integer.parseInt(partsOfCommand[1]) - 1);
            break;
        case "unmark":
            unmark(Integer.parseInt(partsOfCommand[1]) - 1);
            break;
        case "add":
            Task toAdd = deletedTasksList.remove();
            taskList.addAtIndex(toAdd, Integer.parseInt(partsOfCommand[1]) - 1);
            break;
        }
        storage.writeToHistoryFile();
        storage.writeToDeletedTasksFile();
    }


    /**
     * Greets user.
     */
    private void greet() {
        ui.displayOutput("Hello! I'm " + botName + "\n" + "What can I do for you?");
    }

    /**
     * Cleans up and exits.
     */
    public String exit() {
        ui.closeScanner();
        Thread exit = new Thread(){
            public void run(){
                try {
                    Thread.sleep(100);
                    System.exit(0);
                } catch (InterruptedException e) {
                    System.exit(1);
                }
            }
        };
        exit.start();
        return "Bye. Hope to see you again soon!";
    }

}