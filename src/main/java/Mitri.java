import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.min;

public class Mitri {
    String botName;
    String logo;
    Scanner sc;
    ArrayList<Task> taskList;

    public Mitri() {
        this.botName = "Mitri";
        sc = new Scanner(System.in);
        taskList = new ArrayList<Task>(100);
    }
    public void run() {
        greet();
        int running = 1;
        while (running == 1) {
            String input = getInput();
            running = processInput(input);
        }
        exit();
    }

    public void loadFromFile() {
        try{
            Scanner fileScanner = new Scanner(saveFile);
            while (fileScanner.hasNextLine()) {
                taskList.add(readTask(fileScanner.nextLine()));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            //Do nothing
        }
    }

    public Task readTask(String input) {
        String[] parts = input.split(" \\| ");
        Task t;
        switch (parts[0]) {
            case "D":
                t = new Deadline(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length-1)), parts[parts.length-1]);
                break;
            case "E":
                t = new Event(String.join(" | ", Arrays.copyOfRange(parts, 2, parts.length-2)), parts[parts.length-2], parts[parts.length-1]);
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

    public void writeToFile() {
        try {
            FileWriter fileWriter = new FileWriter(saveFile);
            StringBuilder writeStr = new StringBuilder();
            for (Task task : taskList) {
                writeStr.append(writeTask(task)).append("\n");
            }
            fileWriter.write(writeStr.toString());
            fileWriter.close();
        } catch (IOException e) {
            print("Error saving tasks to file. "+e.getMessage());
        }
    }

    public String writeTask(Task task) {
        String output = task.toString();
        switch (output.charAt(1)){
            case 'D':
                output = output.replace(" (by: ", " | ");
                output = output.substring(0, output.length() - 1);
                break;
            case 'E':
                output = output.replace(" (from: ", " | ");
                int to_start = output.indexOf(" to: ", output.indexOf(" | "));
                output = output.substring(0, to_start) + " | " + output.substring(to_start + 5);
                output = output.substring(0, output.length() - 1);
                break;
        }

        output = switch (output.charAt(4)) {
            case 'X' -> output.charAt(1) + " | 1 | " + output.substring(7);
            case ' ' -> output.charAt(1) + " | 0 | " + output.substring(7);
            default -> output;
        };

        return output;
    }

    private String getInput() {
        return sc.nextLine();
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
            print("Error: I'm sorry, but I don't know what that means :(");
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
        } catch (NumberFormatException e) {
            print("Error: Not a number. Please give the index of the task!");
        } catch (IndexOutOfBoundsException e){
            print("Error: Index out of bounds. Please give the correct index!");
        } catch (IllegalArgumentException e) {
            print("Error: " + e.getMessage());
        }

        return 1;
    }

    private void echo(String str){
        print(str);
    }

    private void delete(int index){
        Task t = taskList.remove(index);
        print("Got it. I've removed this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private void addTodo(String str) throws IllegalArgumentException{
        if (str.isBlank()){
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }
        add(new Todo(str.stripLeading()));
    }

    private void addDeadline(String str) throws IllegalArgumentException {
        String[] parts = str.split(" /by ");
        if (parts[0].isBlank() || parts.length == 1 || parts[1].isBlank()){
            throw new IllegalArgumentException("Deadline missing one or more fields. \nEnsure you provide description and by fields.");
        }
        add(new Deadline(parts[0].stripLeading(), parts[1]));
    }

    private void addEvent(String str) throws IllegalArgumentException{
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

        add(new Event(descStr.stripLeading(), fromStr, toStr));
    }

    private void add(Task t){
        taskList.add(t);
        print("Got it. I've added this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private void mark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setDone();
        print("Nice! I've marked this task as done:\n\t" + t);
    }

    private void unmark(int i) throws IndexOutOfBoundsException {
        Task t = taskList.get(i);
        t.setNotDone();
        print("OK, I've marked this task as not done yet:\n\t" + t);
    }

    private void list(){
        StringBuilder printString = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            printString.append("\n\t").append(i + 1).append(". ").append(taskList.get(i));
        }
        print(printString.toString());
    }

    private void greet(){
        print("Hello! I'm " + botName + "\n" + "What can I do for you?");
    }

    private void exit(){
        sc.close();
        print("Bye. Hope to see you again soon!");
    }

    private void print(String str){
        System.out.println("____________________________________________________________");
        System.out.println(str);
        System.out.println("____________________________________________________________");
    }

}