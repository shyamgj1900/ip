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
    private String filePath = "";

    public static final int TASK_POS = 3;
    public static final String GREATER_SYMBOL = ">";
    public static final String DUE_EVENT = "at:";
    public static final String DUE_DEADLINE = "by:";
    public static final String TASK_DONE_SYMBOL = "<X>";
    public static final String PREFIX_DEADLINE = "D";
    public static final String PREFIX_EVENT = "E";
    public static final String PREFIX_TODO = "T";

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads each line of a text file onto an array list of class Task objects.
     * Initially all the content from the file gets copied onto an array list of string.
     * Then each line is checked for the task type and is updated onto the Array list of
     * task objects accordingly.
     *
     * @param task array list of class Task objects.
     * @throws FileNotFoundException exception thrown if file path is not available.
     */
    public void loadFile(ArrayList<Task> task) throws FileNotFoundException {
        ArrayList<String> fileContents = new ArrayList<>();
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            fileContents.add(s.nextLine()); //add each line of the text file to an array list of String
        }
        for (int i = 0; i < fileContents.size(); i++) { //add tasks to array list of class Task objects
            if (fileContents.get(i).startsWith(PREFIX_DEADLINE)) {
                processDeadline(task, fileContents, i);
            } else if (fileContents.get(i).startsWith(PREFIX_EVENT)) {
                processEvent(task, fileContents, i);
            } else if (fileContents.get(i).startsWith(PREFIX_TODO)) {
                processTodo(task, fileContents, i);
            }
        }
    }

    /**
     * Allows writing of text to a file on a new line.
     * New text is appended to the file by using the FileWriter class.
     * Text added appears on new line.
     *
     * @param textToAdd the new text to added to the file.
     * @throws IOException exception is thrown whenever an input or output operation fails.
     */
    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAdd + System.lineSeparator());
        fw.close();
    }

    /**
     * Allows to edit a text file by replacing old text with a new text being passed.
     * Each line in the file is copied to a string. The old string in the file is then
     * replaced with the new string and is written back to the file.
     *
     * @param oldString the string to be replaced.
     * @param newString the string which will replace.
     * @throws IOException exception is thrown whenever an input or output operation fails.
     */
    public void editFile(String oldString, String newString) throws IOException {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader br = new BufferedReader(new FileReader(fileToBeModified));
        String line = br.readLine();
        while (line != null) {
            oldContent = oldContent + line + System.lineSeparator(); //add each line of the text file to a string.
            line = br.readLine();
        }
        String newContent = oldContent.replaceAll(oldString, newString); //replace old string with new string.
        FileWriter fw = new FileWriter(fileToBeModified);
        fw.write(newContent);
        br.close();
        fw.close();
    }

    /**
     * Allows the deletion of a line from the text file.
     * A temporary file is created and all the content except for the content to be
     * deleted is copied onto it. The original file is then deleted and the temporary
     * file is renamed to the original file.
     *
     * @param task the string which is to be deleted from the text file.
     * @throws IOException exception is thrown whenever an input or output operation fails.
     */
    public void deleteTextFromFile(String task) throws IOException {
        File fileToBeModified = new File(filePath);
        File tempFile = new File(fileToBeModified.getAbsolutePath() + ".tmp");
        BufferedReader br = new BufferedReader(new FileReader(fileToBeModified));
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        String line = br.readLine();
        while (line != null) {
            if (!line.equals(task)) { //copy all the content from the old file into the new file except for the content to be deleted
                pw.println(line);
                pw.flush();
            }
            line = br.readLine();
        }
        pw.close();
        br.close();
        fileToBeModified.delete(); //delete old file
        tempFile.renameTo(fileToBeModified); //rename new file
    }

    private void processTodo(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        int greaterSymbolPos = fileContents.get(index).indexOf(GREATER_SYMBOL);
        String todo = fileContents.get(index).substring(greaterSymbolPos + 1);
        task.add(new Todo(todo));
        setTaskDone(task, fileContents, index);
    }

    private void processEvent(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        int greaterSymbolPos = fileContents.get(index).indexOf(GREATER_SYMBOL);
        int atPos = fileContents.get(index).indexOf(DUE_EVENT);
        String event = fileContents.get(index).substring(greaterSymbolPos + 1, atPos);
        String at = fileContents.get(index).substring(atPos + TASK_POS);
        task.add(new Event(event, at));
        setTaskDone(task, fileContents, index);
    }

    private void processDeadline(ArrayList<Task> task, ArrayList<String> fileContents, int index) {
        int greaterSymbolPos = fileContents.get(index).indexOf(GREATER_SYMBOL);
        int byPos = fileContents.get(index).indexOf(DUE_DEADLINE);
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
