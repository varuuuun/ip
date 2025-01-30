package mitri.util;


import mitri.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<Task>(100);
    }

    public Task remove(int index){
        return taskList.remove(index);
    }

    public int size(){
        return taskList.size();
    }

    public void add(Task task){
        taskList.add(task);
    }

    public Task get(int index){
        return taskList.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            sb.append("\n\t").append(i + 1).append(". ").append(taskList.get(i));
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
        for (int i = 0; i < taskList.size(); i++){
            if (taskList.get(i).doesDescriptionContain(string)) {
                list.add(taskList.get(i));
            }
        }

        return list;
    }

}
