package mitri.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import mitri.task.Deadline;

public class ParserTest {
    @Test
    public void parseTaskFromFile_normalInputNotDone_equals() {
        Parser p = new Parser(null, null);
        Deadline d = new Deadline("test", LocalDateTime.parse("2002-05-05T08:00:00"));
        assertEquals(d, p.parseTaskFromFile("D | 0 | test | 2002-05-05T08:00:00"));
    }

    @Test
    public void parseTaskFromFile_normalInputDone_equals() {
        Parser p = new Parser(null, null);
        Deadline d = new Deadline("test", LocalDateTime.parse("2002-05-05T08:00:00"));
        d.setDone();
        assertEquals(d, p.parseTaskFromFile("D | 1 | test | 2002-05-05T08:00:00"));
    }

    @Test
    public void parseTaskFromFile_badInputLessFields_exceptionThrown() {
        Parser p = new Parser(null, null);
        assertThrows(IllegalArgumentException.class, () -> p.parseTaskFromFile("D | 0"));
    }

    @Test
    public void parseTaskFromFile_badInputDateTimeFormat_exceptionThrown() {
        Parser p = new Parser(null, null);
        assertThrows(DateTimeParseException.class, () -> p.parseTaskFromFile("D | 1 | test | 20-02-2002"));
    }
}
