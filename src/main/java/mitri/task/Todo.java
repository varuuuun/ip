package mitri.task;

/**
 * Represents the Todo task.
 */
public class Todo extends Task{

    /**
     * Initialises Todo object.
     *
     * @param description Description of Todo task.
     */
    public Todo(String description){
        super(description);
    }

    /**
     * Converts Todo Task to string for printing purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toString(){
        return "[T]" + super.toString();
    }

    /**
     * Converts Todo Task to string for saving purpose.
     *
     * @return String representation of task.
     */
    @Override
    public String toSave(){
        return "T | " + super.toSave();
    }
}
