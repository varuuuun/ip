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

    private String getInput() {
        return sc.nextLine();
    }

    private int processInput(String input) {
        if (input.equals("bye")) {
            return 0;
        }

        if (input.equals("list")) {
            list();
        } else if (input.startsWith("mark")){
            //TODO: Error checks
            mark(Integer.parseInt(input.split(" ")[1]) - 1);
        } else if (input.startsWith("unmark")){
            //TODO: Error checks
            unmark(Integer.parseInt(input.split(" ")[1]) - 1);
        } else if (input.startsWith("todo ")) {
            addTodo(input.substring(5));
        } else if (input.startsWith("deadline ")) {
            addDeadline(input.substring(9));
        } else if (input.startsWith("event ")) {
            addEvent(input.substring(6));
        } else {
            add(new Task(input));
        }
        return 1;
    }

    private void echo(String str){
        print(str);
    }

    private void addTodo(String str){
        add(new Todo(str));
    }

    private void addDeadline(String str){
        String[] parts = str.split(" /by ");
        add(new Deadline(parts[0], parts[1]));
    }

    private void addEvent(String str){
        int from = str.indexOf(" /from ");
        int to = str.indexOf(" /to ");
        String descStr = str.substring(0,min(from, to));
        String fromStr, toStr;
        if (from < to){
            fromStr = str.substring(from+7,to);
            toStr = str.substring(to+5);
        } else {
            fromStr = str.substring(from+6);
            toStr = str.substring(to+4, from);
        }
        add(new Event(descStr, fromStr, toStr));
    }

    private void add(Task t){
        taskList.add(t);
        print("Got it. I've added this task:\n\t" + t + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private void mark(int i){
        Task t = taskList.get(i);
        t.setDone();
        print("Nice! I've marked this task as done:\n\t" + t);
    }

    private void unmark(int i){
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
