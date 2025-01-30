package mitri.task;

/**
 * Abstract class representing a Task object.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initialises Task object.
     *
     * @param description Description of Task.
     */
    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    /**
     * Sets isDone variable to true, denoting task has been completed.
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * Sets isDone variable to false, denoting task has not been completed.
     */
    public void setNotDone() {
        isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Converts Task to string for printing purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts Task to string for saving purpose.
     *
     * @return String representation of task.
     */
    public String toSave() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Equality check with another object.
     *
     * @param obj Object to compare with task object
     * @return True is task and objects are equal (all fields have the same value), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task task) {
            return this.toString().equals(task.toString());
        }
        return false;
    }
}
