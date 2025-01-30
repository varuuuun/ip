package mitri;

import mitri.chatbot.Mitri;

/**
 * Represents main entry point for the chatbot.
 */
public class Main {

    /**
     * Runs the chatbot.
     *
     * @param args Command-line arguments (unnecessary).
     */
    public static void main(String[] args) {
        Mitri bot = new Mitri();
        bot.run();
    }
}
