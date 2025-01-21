import java.util.ArrayList;
import java.util.Scanner;

public class Bujji {
    String botName;
    String logo;
    Scanner sc;
    ArrayList<Task> taskList;

    public Bujji() {
        this.botName = "Bujji";
        this.logo = "  ____   _    _       _       _  _____ \n"
                + " |  _ \\ | |  | |     | |     | ||_   _|\n"
                + " | |_) || |  | |     | |     | |  | |  \n"
                + " |  _ < | |  | | _   | | _   | |  | |  \n"
                + " | |_) || |__| || |__| || |__| | _| |_ \n"
                + " |____/  \\____/  \\____/  \\____/ |_____|\n";
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
        } else {
            add(input);
        }
        return 1;
    }

    private void echo(String str){
        print(str);
    }

    private void add(String str){
        taskList.add(new Task(str));
        print("added: " + str);
    }

    private void mark(int i){
        Task t = taskList.get(i);
        t.setDone();
        print("Nice! I've marked this task as done: \n\t" + t);
    }

    private void unmark(int i){
        Task t = taskList.get(i);
        t.setNotDone();
        print("OK, I've marked this task as not done yet: \n\t" + t);
    }

    private void list(){
        StringBuilder printString = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            printString.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        print(printString.toString());
    }

    private void greet(){
        print("Hello! I'm " + botName + "\n" + "What can I do for you?");
    }

    private void exit(){
        print("Bye. Hope to see you again soon!");
    }

    private void print(String str){
        System.out.println("____________________________________________________________");
        System.out.println(str);
        System.out.println("____________________________________________________________");
    }

}
