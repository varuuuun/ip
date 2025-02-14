package mitri.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

import mitri.chatbot.Mitri;

/**
 * Represents Storage to deal with file IO.
 */
public class Storage {

    private File saveFile;
    private File historyFile;
    private File deletedTasksFile;
    private Mitri mitri;
    private TaskList taskList;
    private DeletedTasksList deletedTasksList;
    private HistoryList historyList;
    private Parser parser;

    /**
     * Initialises storage object.
     *
     * @param parser Parser to parse commands read.
     * @param mitri Chatbot class to display messages.
     * @param taskList List to store tasks.
     */
    public Storage(Parser parser, Mitri mitri, TaskList taskList, DeletedTasksList deletedTasksList,
                   HistoryList historyList) {
        saveFile = new File("data/mitri.txt");
        historyFile = new File("data/history.txt");
        deletedTasksFile = new File("data/deletedTasks.txt");
        this.parser = parser;
        this.mitri = mitri;
        this.taskList = taskList;
        this.deletedTasksList = deletedTasksList;
        this.historyList = historyList;

        try {
            if (!saveFile.exists()) {
                Files.createDirectories(Paths.get("data"));
                saveFile.createNewFile();
            }
            if (!historyFile.exists()) {
                historyFile.createNewFile();
            }
            if (!deletedTasksFile.exists()) {
                deletedTasksFile.createNewFile();
            }
        } catch (IOException e) {
            mitri.showError(e.getMessage());
        }
    }

    /**
     * Saves tasks by retrieving from task list and writing to file.
     */
    public void writeToSaveFile() {
        writeTaskToFile(saveFile, taskList);
    }

    public void writeTaskToFile(File file, BasicTaskList list){
        try{
            FileWriter fileWriter = new FileWriter(file);
            StringBuilder writeStr = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                writeStr.append(list.get(i).toSave()).append("\n");
            }

            fileWriter.write(writeStr.toString());
            fileWriter.close();
        } catch (IOException e) {
            mitri.showError("Error in saving changes. " + e.getMessage());
        }
    }

    /**
     * Retrieves tasks from saved list and calls parser. Saves returned tasks into task list.
     */
    public void loadFromSaveFile() {
        try {
            Scanner fileScanner = new Scanner(saveFile);

            while (fileScanner.hasNextLine()) {
                taskList.add(parser.parseTaskFromFile(fileScanner.nextLine()));
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            mitri.showError("Save file not found: " + e.getMessage());
        } catch (DateTimeParseException e) {
            mitri.showError("Date/time in wrong format. File may be corrupted.");
        } catch (IllegalArgumentException e) {
            mitri.showError("File is corrupted.");
        }
    }

    public void writeToHistoryFile() {
        try{
            FileWriter fileWriter = new FileWriter(historyFile);
            StringBuilder writeStr = new StringBuilder();

            for (int i = 0; i < historyList.size(); i++) {
                writeStr.append(historyList.get(i)).append("\n");
            }

            fileWriter.write(writeStr.toString());
            fileWriter.close();
        } catch (IOException e) {
            mitri.showError("Error in saving changes. " + e.getMessage());
        }
    }

    public void loadFromHistoryFile() {
        try {
            Scanner fileScanner = new Scanner(historyFile);

            while (fileScanner.hasNextLine()) {
                historyList.addFromFile(fileScanner.nextLine());
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            mitri.showError("History file not found: " + e.getMessage());
        } catch (DateTimeParseException e) {
            mitri.showError("Date/time in wrong format. File may be corrupted.");
        } catch (IllegalArgumentException e) {
            mitri.showError("File is corrupted.");
        }
    }

    public void writeToDeletedTasksFile(String task) {
        writeTaskToFile(deletedTasksFile, deletedTasksList);
    }

    public void loadFromDeletedTasksFile() {
        try {
            Scanner fileScanner = new Scanner(deletedTasksFile);

            while (fileScanner.hasNextLine()) {
                deletedTasksList.addFromFile(parser.parseTaskFromFile(fileScanner.nextLine()));
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            mitri.showError("Save file not found: " + e.getMessage());
        } catch (DateTimeParseException e) {
            mitri.showError("Date/time in wrong format. File may be corrupted.");
        } catch (IllegalArgumentException e) {
            mitri.showError("File is corrupted.");
        }
    }



}
