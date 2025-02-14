package mitri.util;

import java.util.ArrayList;

import mitri.task.Task;

/**
 * Represents Task list.
 */
public class DeletedTasksList extends BasicTaskList {
    private ArrayList<Task> tasks;

    /**
     * Initialises task list.
     */
    public DeletedTasksList() {
        tasks = new ArrayList<Task>(100);
    }

    /**
     * Removes task at top of list.
     *
     * @return Removed task.
     */
    public Task remove() throws IllegalArgumentException{
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("There is no task to delete");
        }
        return tasks.remove(0);
    }

    /**
     * Adds task to list.
     *
     * @param task Task to add to list.
     */
    @Override
    public void add(Task task) {
        tasks.add(0, task);
    }

    public void addFromFile(Task task) {
        tasks.add(task);
    }

    /**
     * Returns current size of list.
     *
     * @return Size of list.
     */
    @Override
    public int size() {
        return tasks.size();
    }

    @Override
    public Task get(int index) {
        return tasks.get(index);
    }

}
