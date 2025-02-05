package mitri.util;

import java.util.ArrayList;

import mitri.task.Task;

/**
 * Represents Task list.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Initialises task list.
     */
    public TaskList() {
        tasks = new ArrayList<Task>(100);
    }

    /**
     * Removes task at specified index of list.
     *
     * @param index Index of task to remove.
     * @return Removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns current size of list.
     *
     * @return Size of list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds task to list.
     *
     * @param task Task to add to list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves task at specified index.
     *
     * @param index Index of task to return.
     * @return Task at specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Converts task list to string.
     *
     * @return String representation of task list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n\t").append(i + 1).append(". ").append(tasks.get(i));
        }

        return sb.toString();
    }

    /**
     * Returns list of tasks that contain substring in their description.
     *
     * @param string Substring to find in Task descriptions.
     * @return TaskList object containing Tasks that contain the given substring in their description.
     */
    public TaskList find(String string) {
        TaskList list = new TaskList();
        for (int i = 0; i < tasks.size(); i++){
            if (tasks.get(i).doesDescriptionContain(string)) {
                list.add(tasks.get(i));
            }
        }

        return list;
    }

}
