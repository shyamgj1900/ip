package duke;

import duke.parser.Parser;
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
    private final Parser parse;

    public Duke(String filePath) {
        ArrayList<Task> tasks = new ArrayList<>();
        ui = new Ui();
        storage = new Storage(filePath, tasks);
        task = new TaskList(filePath, tasks);
        parse = new Parser(filePath, tasks);
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
        parse.processUserInput();
    }

    public static void main(String[] args) {
        String filePath = "data/duke.txt";
        new Duke(filePath).run();
    }
}
