package duke.parser;

import duke.DukeException;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Parser {
    private final Ui ui;
    private final TaskList task;

    public static final int TASK_POS = 3;
    private static final String COMMAND_EXIT = "bye";
    private static final String COMMAND_DISPLAY_TASKS = "list";
    private static final String COMMAND_MARK_DONE = "done";
    private static final String COMMAND_DELETE_TASK = "delete";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_FIND = "find";
    private static final String DEADLINE_DUE = "/by";
    private static final String EVENT_DUE = "/at";

    public Parser(String filePath, ArrayList<Task> tasks) {
        ui = new Ui();
        task = new TaskList(filePath, tasks);
    }

    public void parseCommand() {
        boolean flag = true;
        while (flag) {
            try {
               flag = processUserInput(true);
            } catch (DukeException err) {
                ui.showMediumLine();
                ui.invalidCommandMessage();
                ui.showMediumLine();
            } catch (IndexOutOfBoundsException err) {
                ui.showLongLine();
                System.out.println(err.getMessage());
                ui.showLongLine();
            } catch (NullPointerException err) {
                ui.showMediumLine();
                System.out.println(err.getMessage());
                ui.showMediumLine();
            } catch (IOException err) {
                ui.showMediumLine();
                System.out.println("Something went wrong" + err.getMessage());
                ui.showMediumLine();
            } catch (NumberFormatException err) {
                ui.showShortLine();
                ui.invalidIntegerMessage();
                ui.showShortLine();
            }
        }
    }

    public String getDeadline(String command, int slashPos) {
        command = command.substring(0, slashPos);
        return command.replace(COMMAND_DEADLINE, "");
    }

    public String getEvent(String command, int slashPos) {
        command = command.substring(0, slashPos);
        return command.replace(COMMAND_EVENT, "");
    }

    public String getTodo(String command) {
        return command.replace(COMMAND_TODO, "");
    }

    public int getSlashPos(String command) {
        return command.indexOf("/");
    }

    public String getTaskDuration(String command) {
        if(command.contains(DEADLINE_DUE)) {
            int byPos = command.indexOf(DEADLINE_DUE);
            return command.substring(byPos + TASK_POS);
        } else {
            int atPos = command.indexOf(EVENT_DUE);
            return command.substring(atPos + TASK_POS);
        }
    }

    public int getIndex(String command) {
        String[] splitStr = command.split(" ");
        return Integer.parseInt(splitStr[splitStr.length - 1]);
    }

    public String getFindTask(String command) {
        return command.replace("find", "");
    }

    private boolean processUserInput(boolean flag) throws IOException, DukeException {
        ui.readCommand();
        if (Objects.equals(ui.getCommand(), COMMAND_DISPLAY_TASKS)) {
            task.printList();
        } else if (ui.getCommand().contains(COMMAND_MARK_DONE)) {
            task.markDone(ui.getCommand());
        } else if (ui.getCommand().contains(COMMAND_DELETE_TASK)) {
            task.deleteTask(ui.getCommand());
        } else if (Objects.equals(ui.getCommand(), COMMAND_EXIT)) {
            ui.exitMessage();
            flag = false;
        } else if(ui.getCommand().contains(COMMAND_FIND)) {
            task.searchTask(ui.getCommand());
        } else {
            task.createTask(ui.getCommand());
        }
        return flag;
    }
}
