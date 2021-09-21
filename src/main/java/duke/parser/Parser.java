package duke.parser;

public class Parser {
    public static final int TASK_POS = 3;
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_TODO = "todo";
    private static final String DEADLINE_DUE = "/by";
    private static final String EVENT_DUE = "/at";

    /**
     * The particular deadline task is returned to the user.
     *
     * @param command the task to be resolved.
     * @param slashPos index of the deadline due time.
     * @return the deadline substring of the task.
     */
    public String getDeadline(String command, int slashPos) {
        command = command.substring(0, slashPos);
        return command.replace(COMMAND_DEADLINE, "");
    }

    /**
     * The particular event task is returned to the user.
     *
     * @param command the task to be resolved.
     * @param slashPos index of the event due time.
     * @return the event substring of the task.
     */
    public String getEvent(String command, int slashPos) {
        command = command.substring(0, slashPos);
        return command.replace(COMMAND_EVENT, "");
    }

    /**
     * The particular to-do task is returned to the user.
     *
     * @param command the task to be resolved.
     * @return the to-do substring of the task.
     */
    public String getTodo(String command) {
        return command.replace(COMMAND_TODO, "");
    }

    /**
     * Getter method to indicate the position of the '/' character in the given string.
     *
     * @param command user input which contains the '/' character.
     * @return the index of the '/' character.
     */
    public int getSlashPos(String command) {
        return command.indexOf("/");
    }

    /**
     * Getter method to return the duration or due of the task in the given string.
     *
     * @param command user input which contains the duration or due of the task.
     * @return the duration or due of the task.
     */
    public String getTaskDuration(String command) {
        if(command.contains(DEADLINE_DUE)) {
            int byPos = command.indexOf(DEADLINE_DUE);
            return command.substring(byPos + TASK_POS);
        } else {
            int atPos = command.indexOf(EVENT_DUE);
            return command.substring(atPos + TASK_POS);
        }
    }

    /**
     * Getter method which returns the index of the task to be marked done or deleted.
     *
     * @param command user input which contains the index.
     * @return the index of task on which the action is supposed to take place.
     */
    public int getIndex(String command) {
        String[] splitStr = command.split(" ");
        return Integer.parseInt(splitStr[splitStr.length - 1]);
    }

    /**
     * Getter method which returns the task string which is searched by the user.
     *
     * @param command user input which contains the task string.
     * @return task string searched by the user.
     */
    public String getFindTask(String command) {
        return command.replace("find", "");
    }
}
