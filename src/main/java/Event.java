import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDateTime from, to;
    protected DateTimeFormatter formatter;

    public Event(String description, LocalDateTime from, LocalDateTime to){
        super(description);
        this.from = from;
        this.to = to;
        this.formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss");
    }

    @Override
    public String toString(){
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }
}
