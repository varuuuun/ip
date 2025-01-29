package mitri.util;


import mitri.task.Task;

import java.util.ArrayList;

/**
 * Represents Task list.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Initialises task list.
     */
    public TaskList() {
        taskList = new ArrayList<Task>(100);
    }

    /**
     * Removes task at specified index of list.
     *
     * @param index Index of task to remove.
     * @return Removed task.
     */
    public Task remove(int index){
        return taskList.remove(index);
    }

    /**
     * Gives current size of list.
     *
     * @return Size of list.
     */
    public int size(){
        return taskList.size();
    }

    /**
     * Adds task to list.
     *
     * @param task Task to add to list.
     */
    public void add(Task task){
        taskList.add(task);
    }

    /**
     * Retrieves task at specified index.
     *
     * @param index Index of task to return.
     * @return Task at specified index.
     */
    public Task get(int index){
        return taskList.get(index);
    }

    /**
     * Converts task list to string.
     *
     * @return String representation of task list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            sb.append("\n\t").append(i + 1).append(". ").append(taskList.get(i));
        }
        return sb.toString();
    }




}
