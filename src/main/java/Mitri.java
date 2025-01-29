import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import static java.lang.Math.min;

public class Mitri {
    private String botName;
    private String logo;
    private Ui ui;
    private ArrayList<Task> taskList;
    private Storage storage;

    public Mitri() {
        this.botName = "Mitri";
        ui = new Ui();
        taskList = new ArrayList<Task>(100);
        storage = new Storage(this, ui, taskList);
    }

    public void run() {
        storage.loadFromFile();
        greet();
        int running = 1;
        while (running == 1) {
            String input = ui.getInput();
            running = processInput(input);
        }
        exit();
    }


    public Task readTask(String input) {
        String[] parts = input.split(" \\| ");
        Task t;
        switch (parts[0]) {
            case "D":
                t = new Deadline(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length-1)), LocalDateTime.parse(parts[parts.length-1]));
                break;
            case "E":
                t = new Event(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length-2)), LocalDateTime.parse(parts[parts.length-2]), LocalDateTime.parse(parts[parts.length-1]));
                break;
            default:
                t =  new Todo(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length)));
                break;
        }
        if (parts[1].equals("1")){
            t.setDone();
        } else{
            t.setNotDone();
        }
        return t;
    }



    private int processInput(String input) {
        String[] parts = input.split(" ");
        if (input.equals("bye")) {
            return 0;
        }
        Commands c;
        try {
            c = Commands.getCommand(parts[0]);
        } catch (IllegalArgumentException e){
            ui.printError("I'm sorry, but I don't know what that means :(");
            return 1;
        }
        try {
            switch (c) {
                case LIST:
                    list();
                    break;
                case BYE:
                    return 0;
                case DELETE:
                    delete(Integer.parseInt(parts[1]) - 1);
                    break;
                case MARK:
                    mark(Integer.parseInt(parts[1]) - 1);
                    break;
                case UNMARK:
                    unmark(Integer.parseInt(parts[1]) - 1);
                    break;
                case TODO:
                    addTodo(input.substring(4));
                    break;
                case DEADLINE:
                    addDeadline(input.substring(8));
                    break;
                case EVENT:
                    addEvent(input.substring(5));
                    break;

            }
        } catch (DateTimeParseException e){
            ui.printError("You did not provide a readable date/time");
        } catch (NumberFormatException e) {
            ui.printError("Not a number. Please give the index of the task!");
        } catch (IndexOutOfBoundsException e){
            ui.printError("Index out of bounds. Please give the correct index!");
        } catch (IllegalArgumentException e) {
            ui.printError(e.getMessage());
        }

        return 1;
    }

    private void echo(String str){
        ui.print(str);
    }

    private void delete(int index){
        Task t = taskList.remove(index);
        ui.print("Got it. I've removed this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private void addTodo(String str) throws IllegalArgumentException{
        if (str.isBlank()){
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }
        add(new Todo(str.stripLeading()));
    }

    private void addDeadline(String str) throws IllegalArgumentException, DateTimeParseException {
        String[] parts = str.split(" /by ");
        if (parts[0].isBlank() || parts.length == 1 || parts[1].isBlank()){
            throw new IllegalArgumentException("Deadline missing one or more fields. \nEnsure you provide description and by fields.");
        }
        add(new Deadline(parts[0].stripLeading(), extractDateTime(parts[1])));
    }

    private void addEvent(String str) throws IllegalArgumentException, DateTimeParseException{
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

    private void add(Task t){
        taskList.add(t);
        ui.print("Got it. I've added this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private void mark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setDone();
        ui.print("Nice! I've marked this task as done:\n\t" + t);
    }

    private void unmark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setNotDone();
        ui.print("OK, I've marked this task as not done yet:\n\t" + t);
    }

    private void list(){
        StringBuilder printString = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            printString.append("\n\t").append(i + 1).append(". ").append(taskList.get(i));
        }
        ui.print(printString.toString());
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