import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import static java.lang.Math.min;

public class Mitri {
    private String botName;
    private String logo;
    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private Parser parser;

    public Mitri() {
        this.botName = "Mitri";
        ui = new Ui();
        taskList = new TaskList();
        parser = new Parser(this, ui);
        storage = new Storage(parser, ui, taskList);
    }

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

    private void echo(String str){
        ui.print(str);
    }

    public void delete(int index){
        Task t = taskList.remove(index);
        ui.print("Got it. I've removed this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    public void addTodo(String str) throws IllegalArgumentException{
        if (str.isBlank()){
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }
        add(new Todo(str.stripLeading()));
    }

    public void addDeadline(String str) throws IllegalArgumentException, DateTimeParseException {
        String[] parts = str.split(" /by ");
        if (parts[0].isBlank() || parts.length == 1 || parts[1].isBlank()){
            throw new IllegalArgumentException("Deadline missing one or more fields. \nEnsure you provide description and by fields.");
        }
        add(new Deadline(parts[0].stripLeading(), extractDateTime(parts[1])));
    }

    public void addEvent(String str) throws IllegalArgumentException, DateTimeParseException{
        int from = str.indexOf(" /from ");
        int to = str.indexOf(" /to ");

        if ((from <= 0) || (to <= 0)) {
            throw new IllegalArgumentException("Event missing one or more fields. \nEnsure you provide description, from and to fields.");
        }

        String descStr = str.substring(0,min(from, to));
        String fromStr, toStr;

        if (from < to){
            fromStr = str.substring(from+7,to);
            toStr = str.substring(to+5);
        } else {
            fromStr = str.substring(from+7);
            toStr = str.substring(to+5, from);
        }

        if (descStr.isBlank() || fromStr.isBlank() || toStr.isBlank()) {
            throw new IllegalArgumentException("Event has one or more empty fields. Ensure you provide description, from and to fields.");
        }

        add(new Event(descStr.stripLeading(), extractDateTime(fromStr), extractDateTime(toStr)));
    }

    private LocalDateTime extractDateTime(String str) {
        if (str.indexOf('T')>0){
            return LocalDateTime.parse(str);
        } else if (str.indexOf('-')>0) {
            return LocalDate.parse(str).atStartOfDay();
        } else {
            return LocalDateTime.of(LocalDate.now(), LocalTime.parse(str));
        }
    }

    public void add(Task t){
        taskList.add(t);
        ui.print("Got it. I've added this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    public void mark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setDone();
        ui.print("Nice! I've marked this task as done:\n\t" + t);
    }

    public void unmark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setNotDone();
        ui.print("OK, I've marked this task as not done yet:\n\t" + t);
    }

    public void list(){
        String printString = "Here are the tasks in your list:" + taskList.toString();
        ui.print(printString);
    }

    private void greet(){
        ui.print("Hello! I'm " + botName + "\n" + "What can I do for you?");
    }

    private void exit(){
        ui.closeScanner();
        storage.writeToFile();
        ui.print("Bye. Hope to see you again soon!");
    }

}