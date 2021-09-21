package duke.task;

public class Task {
    protected String task;
    protected boolean isDone;

    public Task() {
        this("");
    }

    public Task(String task) {
        this.task = task;
        this.isDone = false;
    }

    /**
     * Getter method to return the status "done" of a particular task.
     *
     * @return 'X' if task is done else ' ' if task is not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Getter method to return a particular task from the list of tasks.
     *
     * @return task from the task list.
     */
    public String getTask() {
        return task;
    }

    /**
     * Setter method to set whether a particular task is done.
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * The status of a task and the task name are returned which is then used
     * to write to a text file
     *
     * @return the status of a task and task name
     */
    public String fileText() {
        return " <" + this.getStatusIcon() + ">" + this.getTask();
    }

    /**
     * The status of a task and the task name are returned which is then user
     * to store them in a task list.
     *
     * @return the status of a task and the task name.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + this.getTask();
    }
}
