package mitri.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import mitri.ui.Ui;

/**
 * Represents Storage to deal with file IO.
 */
public class Storage {

    private File saveFile;
    private Ui ui;
    private TaskList taskList;
    private Parser parser;

    /**
     * Initialises storage object.
     *
     * @param parser Parser to parse commands read.
     * @param ui Ui to display messages.
     * @param taskList List to store tasks.
     */
    public Storage(Parser parser, Ui ui, TaskList taskList) {
        saveFile = new File("data/mitri.txt");
        this.parser = parser;
        this.ui = ui;
        this.taskList = taskList;

        try {
            if (!saveFile.exists()) {
                Files.createDirectories(Paths.get("data"));
                saveFile.createNewFile();
            }
        } catch (IOException e) {
            ui.printError(e.getMessage());
        }
    }

    /**
     * Saves tasks by retrieving from task list  and writing to file.
     */
    public void writeToFile() {
        try {
            FileWriter fileWriter = new FileWriter(saveFile);
            StringBuilder writeStr = new StringBuilder();

            for (int i = 0; i < taskList.size(); i++) {
                writeStr.append(taskList.get(i).toSave()).append("\n");
            }

            fileWriter.write(writeStr.toString());
            fileWriter.close();
        } catch (IOException e) {
            ui.printError("Could not save tasks to file. " + e.getMessage());
        }
    }

    /**
     * Retrieves tasks from saved list and calls parser. Saves returned tasks into task list.
     */
    public void loadFromFile() {
        try {
            Scanner fileScanner = new Scanner(saveFile);

            while (fileScanner.hasNextLine()) {
                taskList.add(parser.parseTaskFromFile(fileScanner.nextLine()));
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            //Do nothing - should not reach this section
        } catch (DateTimeParseException e) {
            ui.printError("Date/time in wrong format. File may be corrupted.");
        } catch (IllegalArgumentException e) {
            ui.printError("File is corrupted.");
        }
    }


}
