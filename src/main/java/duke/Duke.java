package duke;

import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Duke {
    private static Ui ui;
    private static Storage storage;
    private static TaskList task;

    public Duke(String filePath) {
        ArrayList<Task> tasks = new ArrayList<>();
        ui = new Ui(tasks, filePath);
        storage = new Storage(filePath);
        task = new TaskList(tasks, filePath);
    }

    public void run() {
        ui.welcomeMessage();
        try {
            storage.loadFile(task.getTask());
        } catch (FileNotFoundException err) {
            ui.showLongLine();
            ui.fileNotFoundMessage();
            ui.showLongLine();
        }
        if (task.getTaskSize() != 0) {
            ui.showShortLine();
            ui.taskLoadMessage();
            System.out.println("There are " + task.getTaskSize() + " tasks present");
        } else {
            ui.showShortLine();
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
