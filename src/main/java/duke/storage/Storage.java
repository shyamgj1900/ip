package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    public static final int TASK_POS = 3;
    public static final String GREATER_SYMBOL = ">";
    public static final String EVENT_DUE = "at:";
    public static final String DEADLINE_DUE = "by:";
    public static final String TASK_DONE_SYMBOL = "<X>";
    private String filePath = "";

    public Storage(String filePath) {
        this.filePath = filePath;
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
                processDeadline(task, fileContents, i);
            } else if (fileContents.get(i).startsWith("E")) {
                processEvent(task, fileContents, i);
            } else if (fileContents.get(i).startsWith("T")) {
                processTodo(task, fileContents, i);
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
        BufferedReader br = new BufferedReader(new FileReader(fileToBeModified));
        String line = br.readLine();
        while (line != null) {
            oldContent = oldContent + line + System.lineSeparator();
            line = br.readLine();
        }
        String newContent = oldContent.replaceAll(oldString, newString);
        FileWriter fw = new FileWriter(fileToBeModified);
        fw.write(newContent);
        br.close();
        fw.close();
    }

    public void deleteTextFromFile(String task) throws IOException {
        File fileToBeModified = new File(filePath);
        File tempFile = new File(fileToBeModified.getAbsolutePath() + ".tmp");
        BufferedReader br = new BufferedReader(new FileReader(fileToBeModified));
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        String line = br.readLine();
        while (line != null) {
            if (!line.equals(task)) {
                pw.println(line);
                pw.flush();
            }
            line = br.readLine();
        }
        pw.close();
        br.close();
        fileToBeModified.delete();
        tempFile.renameTo(fileToBeModified);
    }

    private void processTodo(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        int greaterSymbolPos = fileContents.get(index).indexOf(GREATER_SYMBOL);
        String todo = fileContents.get(index).substring(greaterSymbolPos + 1);
        task.add(new Todo(todo));
        setTaskDone(task, fileContents, index);
    }

    private void processEvent(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        int greaterSymbolPos = fileContents.get(index).indexOf(GREATER_SYMBOL);
        int atPos = fileContents.get(index).indexOf(EVENT_DUE);
        String event = fileContents.get(index).substring(greaterSymbolPos + 1, atPos);
        String at = fileContents.get(index).substring(atPos + TASK_POS);
        task.add(new Event(event, at));
        setTaskDone(task, fileContents, index);
    }

    private void processDeadline(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        int greaterSymbolPos = fileContents.get(index).indexOf(GREATER_SYMBOL);
        int byPos = fileContents.get(index).indexOf(DEADLINE_DUE);
        String deadline = fileContents.get(index).substring(greaterSymbolPos + 1, byPos);
        String by = fileContents.get(index).substring(byPos + TASK_POS);
        task.add(new Deadline(deadline, by));
        setTaskDone(task, fileContents, index);
    }

    private void setTaskDone(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        if (fileContents.get(index).contains(TASK_DONE_SYMBOL)) {
            task.get(index).setDone();
        }
    }
}
