package mitri.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    protected DateTimeFormatter formatter;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }

    @Override
    public String toSave() {
        return "D | " + super.toSave() + " | " + by.toString();
    }
}
