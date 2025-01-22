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
            try {
                mark(Integer.parseInt(input.split(" ")[1]) - 1);
            } catch (NumberFormatException e) {
                print("Error: Not a number. Please give the index of the task to mark!");
            } catch (IndexOutOfBoundsException e) {
                print("Error: Index out of bounds. Please give the correct index!");
            }
        } else if (input.startsWith("unmark")){
            try {
                unmark(Integer.parseInt(input.split(" ")[1]) - 1);
            } catch (NumberFormatException e) {
                print("Error: Not a number. Please give the index of the task to unmark!");
            } catch (IndexOutOfBoundsException e) {
                print("Error: Index out of bounds. Please give the correct index!");
            }

        } else if (input.startsWith("todo ")) {
            if (input.length() == 5){
                print("Error: The description of a todo cannot be empty.");
            }
            else {
                addTodo(input.substring(5));
            }
        } else if (input.startsWith("deadline ")) {
            if (input.length() == 9){
                print("Error: Deadline cannot be empty.");
            }
            else {
                try {
                    addDeadline(input.substring(9));
                } catch (IndexOutOfBoundsException e) {
                    print("Error: Deadline missing by field.");
                } catch (IllegalArgumentException e) {
                    print("Error: Deadline missing description.");
                }
            }
        } else if (input.startsWith("event ")) {
            if (input.length() == 6){
                print("Error: Event cannot be empty.");
            }
            else {
                try {
                    addEvent(input.substring(6));
                }
                catch (IllegalArgumentException e){
                    print("Error: Event missing one or more fields. Ensure you provide description, from and by fields.");
                }
            }
        } else {
            print("Error: I'm sorry, but I don't know what that means :(");
        }
        return 1;
    }

    private void echo(String str){
        print(str);
    }

    private void addTodo(String str){
        add(new Todo(str));
    }

    private void addDeadline(String str) throws IndexOutOfBoundsException, IllegalArgumentException {
        String[] parts = str.split(" /by ");
        if (parts[0].isBlank()){
            throw new IllegalArgumentException();
        }
        add(new Deadline(parts[0], parts[1]));
    }

    private void addEvent(String str) throws IllegalArgumentException{
        int from = str.indexOf(" /from ");
        int to = str.indexOf(" /to ");

        if ((from <= 0) || (to <= 0)) {
            throw new IllegalArgumentException();
        }
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
