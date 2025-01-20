public class Bujji {
    String botName;
    String lineDivider;
    String logo;

    public Bujji() {
        this.botName = "Bujji";
        this.lineDivider = "____________________________________________________________";
        this.logo = "  ____   _    _       _       _  _____ \n"
                + " |  _ \\ | |  | |     | |     | ||_   _|\n"
                + " | |_) || |  | |     | |     | |  | |  \n"
                + " |  _ < | |  | | _   | | _   | |  | |  \n"
                + " | |_) || |__| || |__| || |__| | _| |_ \n"
                + " |____/  \\____/  \\____/  \\____/ |_____|\n";
    }
    public void run() {
        greet();
        exit();
    }

    private void greet(){
        print(lineDivider);
        print("Hello! I'm " + botName);
        print("What can I do for you?");
        print(lineDivider);
    }

    private void exit(){
        print("Bye. Hope to see you again soon!");
        System.out.println(lineDivider);
    }

    private void print(String str){
        System.out.println(str);
    }

}
