package mitri.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Deadline task with from and to fields.
 */
public class Event extends Task {
    protected LocalDateTime from, to;
    protected DateTimeFormatter formatter;

    /**
     * Initialises Event object.
     *
     * @param description Description of Task.
     * @param from When this task starts.
     * @param to When this task ends.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss");
    }

    /**
     * Converts Event Task to string for printing purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toOutputString() {
        return "[E]" + super.toOutputString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    /**
     * Converts Event Task to string for saving purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toString() {
        return "E | " + super.toString() + " | " + from.toString() + " | " + to.toString();
    }
}
