package mitri;
import javafx.application.Application;

import mitri.chatbot.Mitri;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Mitri.class, args);
    }
}