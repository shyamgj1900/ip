package duke;

import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Duke {
    private final Ui ui;
    private final Storage storage;
    private final TaskList task;

    public Duke(String filePath) {
        ArrayList<Task> tasks = new ArrayList<>();
        ui = new Ui(tasks, filePath);
        storage = new Storage(filePath);
        task = new TaskList(tasks, filePath);
        try {
            storage.loadFile(task.getTask());
        } catch (FileNotFoundException err) {
            ui.showShortLine();
            ui.fileNotFoundMessage();
        }
    }

    public void run() {
        ui.welcomeMessage();
        if (task.getTaskSize() != 0) {
            ui.taskLoadMessage();
            System.out.println("There are " + task.getTaskSize() + " tasks present");
        } else {
            ui.fileEmptyMessage();
        }
        ui.showShortLine();
        ui.getUserInput();
    }

    public static void main(String[] args) {
        String filePath = "data/duke.txt";
        new Duke(filePath).run();
    }
}
