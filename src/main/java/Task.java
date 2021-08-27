

public class Task {
    private static int listSize = 0;
    public static final int MAX_SIZE = 100;
    protected String task;

    protected boolean isDone;

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
}
