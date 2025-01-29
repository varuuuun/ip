package mitri.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    public void setDone() {
        isDone = true;
    }

    public void setNotDone() {
        isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String toSave(){
        return (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task task) {
            return this.toString().equals(task.toString());
        }
        return false;
    }
}
