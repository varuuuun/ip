import java.util.Scanner;

public class Bujji {
    String botName;
    String lineDivider;
    String logo;
    Scanner sc;

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
        return 1;
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
