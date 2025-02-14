package mitri.util;

import mitri.task.Task;

public abstract class BasicTaskList {
    abstract int size();
    abstract Task get(int index);
    abstract void add(Task task);
}
