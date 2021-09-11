package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        Scanner mySentence = new Scanner(System.in);
        ArrayList<Task> task = new ArrayList<>();
        String file = "data/duke.txt";
        welcomeMessage();
        String sentence = "";
        while (!Objects.equals(sentence, "bye")) {
            try {
                sentence = mySentence.nextLine();
                if (Objects.equals(sentence, "list")) {
                    printList(task);
                } else if (sentence.contains("done")) {
                    markDone(task, sentence, file);
                } else if (Objects.equals(sentence, "bye")) {
                    exitMessage();
                } else if (sentence.contains("delete")) {
                    deleteTask(task, sentence, file);
                } else {
                    createTask(task, sentence, file);
                }
            } catch (DukeException err) {
                System.out.println("---------------------------------------------------------");
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println("---------------------------------------------------------");
            } catch (IndexOutOfBoundsException err) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println(err.getMessage());
                System.out.println("---------------------------------------------------------------------------");
            } catch (NullPointerException err) {
                System.out.println("---------------------------------------------------------");
                System.out.println(err.getMessage());
                System.out.println("---------------------------------------------------------");
            } catch (IOException err) {
                System.out.println("---------------------------------------------------------");
                System.out.println("Something went wrong" + err.getMessage());
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    private static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAdd + System.lineSeparator());
        fw.close();
    }

    private static void overwriteFile(String filePath, String oldString, String newString) throws IOException {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));
        String line = reader.readLine();
        while(line != null) {
            oldContent = oldContent + line + System.lineSeparator();
            line = reader.readLine();
        }
        String newContent = oldContent.replaceAll(oldString, newString);
        FileWriter fw = new FileWriter(fileToBeModified);
        fw.write(newContent);
        reader.close();
        fw.close();
    }

    private static void createTask(ArrayList<Task> task, String sentence, String filePath) throws DukeException, IndexOutOfBoundsException, IOException {
        if (sentence.contains("deadline")) {
            sentence = sentence.replace("deadline", "");
            int slashPos = sentence.indexOf("/");
            if(slashPos < 0) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of a deadline cannot be empty. Must also specify /by");
            }
            String deadline = sentence.substring(0, slashPos);
            String by = null;
            int byPos = sentence.indexOf("/by");
            by = sentence.substring(byPos + 3);
            task.add(new Deadline(deadline, by));
            writeToFile(filePath, task.get(task.size() - 1).fileText());
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(task.get(task.size() - 1).toString());
            System.out.println("Now you have " + task.size() + " tasks in the list");
            System.out.println("-----------------------------------");
        } else if (sentence.contains("event")) {
            sentence = sentence.replace("event", "");
            int slashPos = sentence.indexOf("/");
            if(slashPos < 0) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of an event cannot be empty. Must also specify /at");
            }
            String event = sentence.substring(0, slashPos);
            String at = null;
            int atPos = sentence.indexOf("/at");
            at = sentence.substring(atPos + 3);
            task.add(new Event(event, at));
            writeToFile(filePath, task.get(task.size() - 1).fileText());
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(task.get(task.size() - 1).toString());
            System.out.println("Now you have " + task.size() + " tasks in the list");
            System.out.println("-----------------------------------");
        } else if (sentence.contains("todo")) {
            String todo = sentence.replace("todo", "");
            if(todo.equals("")) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of a todo cannot be empty.");
            }
            task.add(new Todo(todo));
            writeToFile(filePath, task.get(task.size() - 1).fileText());
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(task.get(task.size() - 1).toString());
            System.out.println("Now you have " + task.size() + " tasks in the list");
            System.out.println("-----------------------------------");
        } else {
            throw new DukeException();
        }
    }

    public static void deleteTask(ArrayList<Task> task, String word, String filePath) throws NullPointerException, IOException {
        String num = word.substring(word.length() - 1);
        int index = Integer.parseInt(num);
        if(index > task.size()) {
            throw new NullPointerException("Number given is more than the number of tasks in list");
        }
        String oldString = task.get(index - 1).fileText();
        overwriteFile(filePath, oldString, "");
        System.out.println("-----------------------------------");
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.get(index - 1).toString());
        task.remove(index - 1);
        System.out.println("Now you have " + task.size() + " tasks in the list");
        System.out.println("-----------------------------------");
    }

    private static void markDone(ArrayList<Task> task, String word, String filePath) throws NullPointerException, IOException {
        String num = word.substring(word.length() - 1);
        int index = Integer.parseInt(num);
        if(index > task.size()) {
            throw new NullPointerException("Number given is more than the number of tasks in list");
        }
        String oldString = task.get(index - 1).fileText();
        task.get(index - 1).setDone();
        overwriteFile(filePath, oldString, task.get(index - 1).fileText());
        System.out.println("-----------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.get(index - 1).toString());
        System.out.println("-----------------------------------");
    }

    private static void printList(ArrayList<Task> task) {
        System.out.println("-----------------------------------");
        if(task.size() == 0) {
            System.out.println("No tasks in list");
        }
        else {
            for (int i = 0; i < task.size(); i++) {
                System.out.println(i + 1 + ". " + task.get(i).toString());
            }
        }
        System.out.println("-----------------------------------");
    }

    private static void welcomeMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("-----------------------------------");
    }

    private static void exitMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-----------------------------------");
    }
}
