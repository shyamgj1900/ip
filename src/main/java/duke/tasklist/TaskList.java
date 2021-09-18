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
    private ArrayList<Task> tasks;
    private final String filePath;
    private final Ui ui;
    private final Storage storage;

    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String TODO_COMMAND = "todo";

    public TaskList(String filePath, ArrayList<Task> tasks) {
        this.filePath = filePath;
        this.tasks = tasks;
        ui = new Ui();
        storage = new Storage(filePath);
    }

    public void createTask(String command) throws DukeException, IndexOutOfBoundsException, IOException {
        Parser parse = new Parser(filePath, tasks);
        if (command.contains(DEADLINE_COMMAND)) {
            int slashPos = parse.getSlashPos(command);
            if (slashPos < 0) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of a deadline cannot be empty. Must also specify /by");
            }
            String deadline = parse.getDeadline(command, slashPos);
            String by = parse.getTaskDuration(command);
            tasks.add(new Deadline(deadline, by));
            storage.writeToFile(tasks.get(tasks.size() - 1).fileText());
            ui.showShortLine();
            ui.taskAddedMessage();
            System.out.println(tasks.get(tasks.size() - 1).toString());
            System.out.println("Now you have " + tasks.size() + " tasks in the list");
            ui.showShortLine();
        } else if (command.contains(EVENT_COMMAND)) {
            int slashPos = parse.getSlashPos(command);
            if (slashPos < 0) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of an event cannot be empty. Must also specify /at");
            }
            String event = parse.getEvent(command, slashPos);
            String at = parse.getTaskDuration(command);
            tasks.add(new Event(event, at));
            storage.writeToFile(tasks.get(tasks.size() - 1).fileText());
            ui.showShortLine();
            ui.taskAddedMessage();
            System.out.println(tasks.get(tasks.size() - 1).toString());
            System.out.println("Now you have " + tasks.size() + " tasks in the list");
            ui.showShortLine();
        } else if (command.contains(TODO_COMMAND)) {
            String todo = parse.getTodo(command);
            if (todo.equals("")) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of a todo cannot be empty.");
            }
            tasks.add(new Todo(todo));
            storage.writeToFile(tasks.get(tasks.size() - 1).fileText());
            ui.showShortLine();
            ui.taskAddedMessage();
            System.out.println(tasks.get(tasks.size() - 1).toString());
            System.out.println("Now you have " + tasks.size() + " tasks in the list");
            ui.showShortLine();
        } else {
            throw new DukeException();
        }
    }

    public void deleteTask(String command) throws NullPointerException, IOException, NumberFormatException {
        Parser parse = new Parser(filePath, tasks);
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

    public void markDone(String command) throws NullPointerException, IOException, NumberFormatException {
        Parser parse = new Parser(filePath, tasks);
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

    public void searchTask(String command) {
        Parser parse = new Parser(filePath, tasks);
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

    public void printList() {
        Ui ui = new Ui();
        ui.showShortLine();
        if (tasks.size() == 0) {
            ui.noTaskMessage();
        }
        else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i).toString());
            }
        }
        ui.showShortLine();
    }

    public ArrayList<Task> getTask() {
       return tasks;
    }

    public int getTaskSize() {
        return tasks.size();
    }
}
