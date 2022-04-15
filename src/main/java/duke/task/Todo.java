package duke.task;

public class Todo extends Task {
    public Todo(String task) {
        super(task);
    }

    @Override
    public String fileText() {
        return "T" + super.fileText();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
