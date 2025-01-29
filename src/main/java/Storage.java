import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private File saveFile;
    private Ui ui;
    private ArrayList<Task> taskList;
    private Mitri mitri;

    public Storage(Mitri mitri, Ui ui, ArrayList<Task> taskList) {
        saveFile = new File("data/mitri.txt");
        this.mitri = mitri;
        this.ui = ui;
        this.taskList = taskList;
        try {
            if (!saveFile.exists()) {
                Files.createDirectories(Paths.get("data"));
                saveFile.createNewFile();
            }
        }catch (IOException e){
            ui.printError(e.getMessage());
        }
    }

    public void writeToFile() {
        try {
            FileWriter fileWriter = new FileWriter(saveFile);
            StringBuilder writeStr = new StringBuilder();
            for (Task task : taskList) {
                writeStr.append(task.toSave()).append("\n");
            }
            fileWriter.write(writeStr.toString());
            fileWriter.close();
        } catch (IOException e) {
            ui.printError("Could not save tasks to file. " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try{
            Scanner fileScanner = new Scanner(saveFile);
            while (fileScanner.hasNextLine()) {
                taskList.add(mitri.readTask(fileScanner.nextLine()));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            //Do nothing - should not reach this section
        }
    }


}
