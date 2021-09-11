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

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getTask() {
        return task;
    }

    public void setDone() {
        isDone = true;
    }

    public String fileText() {
        return " <" + this.getStatusIcon() + ">" + this.getTask();
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + this.getTask();
    }
}
