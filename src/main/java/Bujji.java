public class Bujji {
    public static void main(String[] args) {
        greet();
        exit();
    }

    public static void greet(){
        String botName = "Bujji";
        String lineDivider = "____________________________________________________________";
        String logo = "  ____   _    _       _       _  _____ \n"
                + " |  _ \\ | |  | |     | |     | ||_   _|\n"
                + " | |_) || |  | |     | |     | |  | |  \n"
                + " |  _ < | |  | | _   | | _   | |  | |  \n"
                + " | |_) || |__| || |__| || |__| | _| |_ \n"
                + " |____/  \\____/  \\____/  \\____/ |_____|\n";
        System.out.println(lineDivider);
        System.out.println("Hello! I'm " + botName);
        System.out.println("What can I do for you?");
        System.out.println(lineDivider);
    }

    public static void exit(){
        String lineDivider = "____________________________________________________________";
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(lineDivider);
    }

}
