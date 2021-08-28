

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

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + getTask();
    }
}
