import java.util.ArrayList;
import java.util.Scanner;

public class Bujji {
    String botName;
    String lineDivider;
    String logo;
    Scanner sc;
    ArrayList<String> taskList;

    public Bujji() {
        this.botName = "Bujji";
        this.lineDivider = "____________________________________________________________";
        this.logo = "  ____   _    _       _       _  _____ \n"
                + " |  _ \\ | |  | |     | |     | ||_   _|\n"
                + " | |_) || |  | |     | |     | |  | |  \n"
                + " |  _ < | |  | | _   | | _   | |  | |  \n"
                + " | |_) || |__| || |__| || |__| | _| |_ \n"
                + " |____/  \\____/  \\____/  \\____/ |_____|\n";
        sc = new Scanner(System.in);
        taskList = new ArrayList<String>(100);
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
        } else {
            add(input);
        }
        return 1;
    }

    private void echo(String str){
        print(lineDivider);
        print(str);
        print(lineDivider);
    }

    private void add(String str){
        taskList.add(str);
        print(lineDivider);
        print("added: " + str);
        print(lineDivider);
    }

    private void list(){
        print(lineDivider);
        for (int i = 0; i < taskList.size(); i++) {
            print(i+1 + ". " + taskList.get(i));
        }
        print(lineDivider);
    }

    private void greet(){
        print(lineDivider);
        print("Hello! I'm " + botName);
        print("What can I do for you?");
        print(lineDivider);
    }

    private void exit(){
        print(lineDivider);
        print("Bye. Hope to see you again soon!");
        print(lineDivider);
    }

    private void print(String str){
        System.out.println(str);
    }

}
