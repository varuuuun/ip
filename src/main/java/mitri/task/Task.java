package mitri.task;

/**
 * Represents a Task object.
 * Abstract class, cannot and should not be instantiated.
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

    public String toOutputString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts Task to string for saving purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Checks equality against another object.
     *
     * @param obj Object to compare with task object
     * @return True is task and objects are equal (all fields have the same value), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task task) {
            return this.toOutputString().equals(task.toOutputString());
        }
        return false;
    }

    /**
     * Returns whether given string is a substring of Description field.
     *
     * @param string Substring to search in Description.
     * @return True if Description contains substring, false otherwise.
     */
    public boolean doesDescriptionContain(String string) {
        return description.contains(string);
    }
}
