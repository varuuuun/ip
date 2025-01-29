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




}
