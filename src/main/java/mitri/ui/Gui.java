package mitri.ui;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import mitri.util.Parser;

public class Gui extends Ui {

    @javafx.fxml.FXML
    private ScrollPane scrollPane;
    @javafx.fxml.FXML
    private VBox dialogContainer;
    @javafx.fxml.FXML
    private TextField userInput;
    @javafx.fxml.FXML
    private Button sendButton;
    private Scene scene;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image mitriImage = new Image(this.getClass().getResourceAsStream("/images/chatbot.png"));
    private Parser parser;

    public Gui() {
        this.dialogContainer = new VBox();
    }

    public Gui(Parser parser) {
        this.parser = parser;
    }

    public void start(Stage stage){
        //Setting up required components

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/Gui.fxml"));
            AnchorPane ap = fxmlLoader.load();
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        displayOutput("Hello! I'm Mitri\nWhat can I do for you?");
    }

    @FXML
    private void handleUserInput() {
        assert parser != null : "Parser should not be null before processing input";
        String userText = userInput.getText();
        dialogContainer.getChildren().addAll(
                mitri.ui.DialogBox.getUserDialog(userText, userImage)
        );
        userInput.clear();
        dialogContainer.getChildren().addAll(
                mitri.ui.DialogBox.getMitriDialog(parser.processInput(userText), mitriImage)
        );

    }

    @FXML
    public void displayOutput(String output){
        dialogContainer.getChildren().addAll(
                mitri.ui.DialogBox.getMitriDialog(output, mitriImage)
        );
    }

    @FXML
    public void displayError(String error){
        dialogContainer.getChildren().addAll(
                mitri.ui.DialogBox.getMitriDialog("Error: " + error, mitriImage)
        );
    }

    public String getError(String error){
        return "Error: " + error;
    }

}
