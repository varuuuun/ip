package mitri.util;

import mitri.task.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    @Test
    public void removeTest_indexWithinSize_noError() {
        TaskList list = new TaskList();
        list.add(new Todo("hello"));
        list.add(new Todo("hi"));
        list.remove(1);
        assert(list.size() == 1);
    }

    @Test
    public void removeTest_tooLargeIndex_exceptionThrown() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, ()->list.remove(1));
    }

    @Test
    public void removeTest_tooSmallIndex_exceptionThrown() {
        TaskList list = new TaskList();
        list.add(new Todo("hello"));
        list.add(new Todo("hi"));
        assertThrows(IndexOutOfBoundsException.class, ()-> list.remove(-1));
    }

}
