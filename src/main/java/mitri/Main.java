package mitri;

import mitri.chatbot.Mitri;

/**
 * Main entry point for the application.
 */
public class Main {

    /**
     * The main method that runs the application.
     *
     * @param args Command-line arguments (unnecessary).
     */
    public static void main(String[] args) {
        Mitri bot = new Mitri();
        bot.run();
    }
}
