package mitri.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Deadline task with deadline to complete by.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    protected DateTimeFormatter formatter;

    /**
     * Initialises Deadline object.
     *
     * @param description Description of Task.
     * @param by Deadline to complete this task by.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss");
    }

    /**
     * Converts Deadline Task to string for printing purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toOutputString() {
        return "[D]" + super.toOutputString() + " (by: " + by.format(formatter) + ")";
    }

    /**
     * Converts Deadline Task to string for saving purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toString() {
        return "D | " + super.toString() + " | " + by.toString();
    }
}
