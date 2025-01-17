package duke.task;

public class Event extends Task {
    protected String at;

    public Event(String task, String at) {
        super(task);
        this.at = at;
    }

    @Override
    public String fileText() {
        return "E" + super.fileText() + "at:" + this.at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at:" + this.at + ")";
    }
}
