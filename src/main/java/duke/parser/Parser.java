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

    private static final String EXIT_COMMAND = "bye";
    private static final String DISPLAY_TASKS_COMMAND = "list";
    private static final String MARK_DONE_COMMAND = "done";
    private static final String DELETE_TASK_COMMAND = "delete";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String TODO_COMMAND = "todo";
    private static final String FIND_COMMAND = "find";

    public Parser(String filePath, ArrayList<Task> tasks) {
        ui = new Ui();
        task = new TaskList(filePath, tasks);
    }

    public void processUserInput() {
        boolean flag = true;
        while (flag) {
            try {
                ui.readCommand();
                if (Objects.equals(ui.getCommand(), DISPLAY_TASKS_COMMAND)) {
                    task.printList();
                } else if (ui.getCommand().contains(MARK_DONE_COMMAND)) {
                    task.markDone(ui.getCommand());
                } else if (ui.getCommand().contains(DELETE_TASK_COMMAND)) {
                    task.deleteTask(ui.getCommand());
                } else if (Objects.equals(ui.getCommand(), EXIT_COMMAND)) {
                    ui.exitMessage();
                    flag = false;
                } else if(ui.getCommand().contains(FIND_COMMAND)) {
                      task.searchTask(ui.getCommand());
                } else {
                    task.createTask(ui.getCommand());
                }
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
        return command.replace(DEADLINE_COMMAND, "");
    }

    public String getEvent(String command, int slashPos) {
        command = command.substring(0, slashPos);
        return command.replace(EVENT_COMMAND, "");
    }

    public String getTodo(String command) {
        return command.replace(TODO_COMMAND, "");
    }

    public int getSlashPos(String command) {
        return command.indexOf("/");
    }

    public String getTaskDuration(String command) {
        if(command.contains("/by")) {
            int byPos = command.indexOf("/by");
            return command.substring(byPos + 3);
        } else {
            int atPos = command.indexOf("/at");
            return command.substring(atPos + 3);
        }
    }

    public int getIndex(String command) {
        String[] splitStr = command.split(" ");
        return Integer.parseInt(splitStr[splitStr.length - 1]);
    }

    public String getFindTask(String command) {
        return command.replace("find", "");
    }
}
