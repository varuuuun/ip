package mitri.ui;

import java.util.Scanner;

/**
 * Represents the Ui.
 */
public class Ui {
    private Scanner sc;

    /**
     * Initialises scanner for the Ui.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Retrieves input from command line.
     *
     * @return Given input.
     */
    public String getInput() {
        return sc.nextLine();
    }

    /**
     * Closes scanner during cleanup.
     */
    public void closeScanner() {
        sc.close();
    }

    /**
     * Formats and prints given input.
     *
     * @param str String to print.
     */
    public void displayOutput(String str) {
        System.out.println("____________________________________________________________");
        System.out.println(str);
        System.out.println("____________________________________________________________");
    }

    /**
     * Formats and prints given Error message.
     * Currently unused, but will be used when chatbot is used as a CLI agent.
     *
     * @param str Error Message to print.
     */
    public void displayError(String str) {
        System.out.println("____________________________________________________________");
        System.out.println("Error: " + str);
        System.out.println("____________________________________________________________");
    }


}
