package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath = "";
    private ArrayList<Task> tasks;

    public Storage(String filePath, ArrayList<Task> tasks) {
        this.filePath = filePath;
        this.tasks = tasks;
    }

    public void loadFile(ArrayList<Task> task) throws FileNotFoundException {
        ArrayList<String> fileContents = new ArrayList<>();
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            fileContents.add(s.nextLine());
        }
        for (int i = 0; i < fileContents.size(); i++) {
            if (fileContents.get(i).startsWith("D")) {
                int greaterSymbolPos = fileContents.get(i).indexOf(">");
                int byPos = fileContents.get(i).indexOf("by:");
                String deadline = fileContents.get(i).substring(greaterSymbolPos + 1, byPos);
                String by = fileContents.get(i).substring(byPos + 3);
                task.add(new Deadline(deadline, by));
                if (fileContents.get(i).contains("<X>")){
                    task.get(i).setDone();
                }
            } else if (fileContents.get(i).startsWith("E")) {
                int greaterSymbolPos = fileContents.get(i).indexOf(">");
                int atPos = fileContents.get(i).indexOf("at:");
                String event = fileContents.get(i).substring(greaterSymbolPos + 1, atPos);
                String at = fileContents.get(i).substring(atPos + 3);
                task.add(new Event(event, at));
                if (fileContents.get(i).contains("<X>")){
                    task.get(i).setDone();
                }
            } else if (fileContents.get(i).startsWith("T")) {
                int greaterSymbolPos = fileContents.get(i).indexOf(">");
                String todo = fileContents.get(i).substring(greaterSymbolPos + 1);
                task.add(new Todo(todo));
                if (fileContents.get(i).contains("<X>")){
                    task.get(i).setDone();
                }
            }
        }
    }

    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAdd + System.lineSeparator());
        fw.close();
    }

    public void editFile(String oldString, String newString) throws IOException {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));
        String line = reader.readLine();
        while (line != null) {
            oldContent = oldContent + line + System.lineSeparator();
            line = reader.readLine();
        }
        String newContent = oldContent.replaceAll(oldString, newString);
        FileWriter fw = new FileWriter(fileToBeModified);
        fw.write(newContent);
        reader.close();
        fw.close();
    }
}
