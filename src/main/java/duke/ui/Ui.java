package duke.ui;

import duke.DukeException;
import duke.task.Task;
import duke.tasklist.TaskList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Ui {
    private String command = "";
    private final ArrayList<Task> tasks;
    private String filePath = "";

    private static final String COMMAND_EXIT = "bye";
    private static final String COMMAND_DISPLAY_TASKS = "list";
    private static final String COMMAND_MARK_DONE = "done";
    private static final String COMMAND_DELETE_TASK = "delete";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_HELP = "help";

    public Ui(ArrayList<Task> tasks, String filePath) {
        this.tasks = tasks;
        this.filePath = filePath;
    }

    /**
     * Input from the user is read till user inputs command "bye".
     * The input command is read from the user using the readCommand() method from the Ui
     * class and is passed to the "processUserInput()" method to resolve the user input further.
     */
    public void getUserInput() {
        boolean flag = true;
        while (flag) {
            try {
                readCommand(); //read input from user
                flag = processUserInput(true);
            } catch (DukeException err) {
                showMediumLine();
                invalidCommandMessage();
                showMediumLine();
            } catch (IndexOutOfBoundsException err) {
                showLongLine();
                System.out.println(err.getMessage());
                showLongLine();
            } catch (NullPointerException err) {
                showMediumLine();
                System.out.println(err.getMessage());
                showMediumLine();
            } catch (IOException err) {
                showMediumLine();
                System.out.println("Something went wrong" + err.getMessage());
                showMediumLine();
            } catch (NumberFormatException err) {
                showShortLine();
                invalidIntegerMessage();
                showShortLine();
            }
        }
    }

    /**
     * Getter method to return command entered by the user.
     *
     * @return the command entered by the user.
     */
    public String getCommand() {
        return this.command;
    }

    public void welcomeMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("Enter \"help\" to see the list of commands");
        System.out.println("-----------------------------------");
    }

    public void exitMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-----------------------------------");
    }

    public void showShortLine() {
        System.out.println("-----------------------------------");
    }

    public void showMediumLine() {
        System.out.println("---------------------------------------------------------");
    }

    public void showLongLine() {
        System.out.println("---------------------------------------------------------------------------");
    }

    public void fileNotFoundMessage() {
        System.out.println("File not found. Don't worry we have created a new text file for you.");
    }

    public void taskLoadMessage() {
        System.out.println("Tasks loaded from file!!!!");
    }

    public void fileEmptyMessage() {
        System.out.println("Tasks file is currently empty.");
    }

    public void invalidCommandMessage() {
        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    public void invalidIntegerMessage() {
        System.out.println("Please specify an index number");
    }

    public void taskAddedMessage() {
        System.out.println("Got it. I've added this task");
    }

    public void taskRemovedMessage() {
        System.out.println("Noted. I've removed this task:");
    }

    public void taskDoneMessage() {
        System.out.println("Nice! I've marked this task as done:");
    }

    public void noTaskMessage() {
        System.out.println("No tasks in list");
    }

    public void taskFoundMessage() {
        System.out.println("Here are the matching tasks in your list:");
    }

    public void taskNotFoundMessage() {
        System.out.println("No tasks match the given query");
    }

    public void helpMessage() {
        showMediumLine();
        System.out.println("1) Add a deadline task." + System.lineSeparator() + "Example: deadline TASK_DESCRIPTION /by DEADLINE_TIME");
        System.out.println("2) Add an event task." + System.lineSeparator() + "Example: event TASK_DESCRIPTION /at EVENT_TIME");
        System.out.println("3) Add a todo task." + System.lineSeparator() + "Example: todo TASK_DESCRIPTION");
        System.out.println("4) List all tasks." + System.lineSeparator() + "Example: list");
        System.out.println("5) Mark task as done." + System.lineSeparator() + "Example: done TASK_INDEX_NUMBER");
        System.out.println("6) Find task in list." + System.lineSeparator() + "Example: find TASK_NAME");
        System.out.println("7) Delete a task." + System.lineSeparator() + "Example: delete TASK_INDEX_NUMBER");
        System.out.println("8) Exit application." + System.lineSeparator() + "Example: bye");
        showMediumLine();
    }

    private void readCommand() {
        Scanner myCommand = new Scanner(System.in);
        command = myCommand.nextLine();
    }

    /**
     * Checks the user input for a particular command to execute a particular action.
     *
     * @param flag checks whether further instruction need to be executed
     * @return true if further actions are to be executed, false otherwise
     * @throws IOException exception is thrown whenever an input or output operation fails.
     * @throws DukeException exception is thrown whenever user inputs an invalid command.
     */
    private boolean processUserInput(boolean flag) throws IOException, DukeException {
        TaskList task = new TaskList(tasks, filePath);
        if (Objects.equals(getCommand(), COMMAND_DISPLAY_TASKS)) {
            task.printList();
        } else if (getCommand().contains(COMMAND_MARK_DONE)) {
            task.markDone(getCommand());
        } else if (getCommand().contains(COMMAND_DELETE_TASK)) {
            task.deleteTask(getCommand());
        } else if (getCommand().contains(COMMAND_FIND)) {
            task.searchTask(getCommand());
        } else if (Objects.equals(getCommand(), COMMAND_HELP)) {
            helpMessage();
        } else if (Objects.equals(getCommand(), COMMAND_EXIT)) {
            exitMessage();
            flag = false;
        } else {
            task.createTask(getCommand());
        }
        return flag;
    }
}
