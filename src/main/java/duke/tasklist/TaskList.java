package duke.tasklist;

import duke.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    private final Parser parse;
    private final Storage storage;
    private final Ui ui;

    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_TODO = "todo";


    public TaskList(ArrayList<Task> tasks, String filePath) {
        this.tasks = tasks;
        parse = new Parser();
        storage = new Storage(filePath);
        ui = new Ui(tasks, filePath);
    }

    /**
     * Creates a new task by adding the task to an Array list of class Task objects.
     * Task to be added is checked whether it is of type deadline, event or to-do.
     * Given text file is also updated accordingly.
     *
     * @param command task to be added to the array list of class Task objects.
     * @throws DukeException exception is thrown whenever an invalid input is given by the user.
     * @throws IOException exception is thrown whenever an input or output operation fails.
     */
    public void createTask(String command) throws DukeException, IOException {
        if (command.contains(COMMAND_DEADLINE)) {
            processDeadline(command);
        } else if (command.contains(COMMAND_EVENT)) {
            processEvent(command);
        } else if (command.contains(COMMAND_TODO)) {
            processTodo(command);
        } else {
            throw new DukeException();
        }
    }

    /**
     * Deletes a task from the array list of class Task object.
     * Task to be deleted is passed from the user and is removed from the array list.
     * Given text file is also updated accordingly.
     *
     * @param command task to be deleted from the array list
     * @throws NullPointerException exception is thrown when null is used in a case where object is required.
     * @throws IOException exception is thrown whenever an input or output operation fails.
     * @throws NumberFormatException exception is thrown whenever given number is not of correct format.
     */
    public void deleteTask(String command) throws NullPointerException, IOException, NumberFormatException {
        int index = parse.getIndex(command);
        if (index > tasks.size()) {
            throw new NullPointerException("Number given is more than the number of tasks in list");
        }
        String taskToBeDeleted = tasks.get(index - 1).fileText();
        storage.deleteTextFromFile(taskToBeDeleted);
        ui.showShortLine();
        ui.taskRemovedMessage();
        System.out.println(tasks.get(index - 1).toString());
        tasks.remove(index - 1);
        System.out.println("Now you have " + tasks.size() + " tasks in the list");
        ui.showShortLine();
    }

    /**
     * Marks a given task as done in the array list of class Task objects.
     * Task to be marked done is passed from user and is marked as done in the array list.
     * Given text file is also updated accordingly.
     *
     * @param command task to be deleted from the array list
     * @throws NullPointerException exception is thrown when null is used in a case where object is required.
     * @throws IOException exception is thrown whenever an input or output operation fails.
     * @throws NumberFormatException exception is thrown whenever given number is not of correct format.
     */
    public void markDone(String command) throws NullPointerException, IOException, NumberFormatException {
        int index = parse.getIndex(command);
        if (index > tasks.size()) {
            throw new NullPointerException("Number given is more than the number of tasks in list");
        }
        String oldString = tasks.get(index - 1).fileText();
        tasks.get(index - 1).setDone();
        storage.editFile(oldString, tasks.get(index - 1).fileText());
        ui.showShortLine();
        ui.taskDoneMessage();
        System.out.println(tasks.get(index - 1).toString());
        ui.showShortLine();
    }

    /**
     * Tasks are displayed according to the users search query.
     * User can give a task name they want to view. The respective task is
     * then displayed on the screen if the given tasks matches any of the tasks
     * in the list, else no tasks are displayed.
     *
     * @param command task to be found and displayed.
     */
    public void searchTask(String command) {
        String task = parse.getFindTask(command);
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getTask().contains(task)) {
                if(count == 0) {
                    ui.showShortLine();
                    ui.taskFoundMessage();
                }
                System.out.println(count + 1 + ". " + tasks.get(i).toString());
                count++;
            }
        }
        if(count == 0) {
            ui.showShortLine();
            ui.taskNotFoundMessage();
        }
        ui.showShortLine();
    }

    /**
     * Prints the array list of class Task objects. No tasks are printed if the
     * array list is empty.
     */
    public void printList() {
        ui.showShortLine();
        if (tasks.size() == 0) {
            ui.noTaskMessage();
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i).toString());
            }
        }
        ui.showShortLine();
    }

    /**
     * Getter method which returns the array list.
     *
     * @return the array list of class Task objects.
     */
    public ArrayList<Task> getTask() {
       return tasks;
    }

    /**
     * Getter method which the size of the array list.
     *
     * @return the array list of class Task objects size.
     */
    public int getTaskSize() {
        return tasks.size();
    }

    private void processDeadline(String command) throws IndexOutOfBoundsException, IOException {
        int slashPos = parse.getSlashPos(command);
        if (slashPos < 0) {
            throw new IndexOutOfBoundsException("OOPS!!! The description of a deadline cannot be empty. Must also specify /by");
        }
        String deadline = parse.getDeadline(command, slashPos);
        String by = parse.getTaskDuration(command);
        tasks.add(new Deadline(deadline, by));
        storage.writeToFile(tasks.get(tasks.size() - 1).fileText());
        taskAdded();
    }

    private void processEvent(String command) throws IndexOutOfBoundsException, IOException {
        int slashPos = parse.getSlashPos(command);
        if (slashPos < 0) {
            throw new IndexOutOfBoundsException("OOPS!!! The description of an event cannot be empty. Must also specify /at");
        }
        String event = parse.getEvent(command, slashPos);
        String at = parse.getTaskDuration(command);
        tasks.add(new Event(event, at));
        storage.writeToFile(tasks.get(tasks.size() - 1).fileText());
        taskAdded();
    }

    private void processTodo(String command) throws IndexOutOfBoundsException, IOException {
        String todo = parse.getTodo(command);
        if (todo.equals("")) {
            throw new IndexOutOfBoundsException("OOPS!!! The description of a todo cannot be empty.");
        }
        tasks.add(new Todo(todo));
        storage.writeToFile(tasks.get(tasks.size() - 1).fileText());
        taskAdded();
    }

    private void taskAdded() {
        ui.showShortLine();
        ui.taskAddedMessage();
        System.out.println(tasks.get(tasks.size() - 1).toString());
        System.out.println("Now you have " + tasks.size() + " tasks in the list");
        ui.showShortLine();
    }
}
