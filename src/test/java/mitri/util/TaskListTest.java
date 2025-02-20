package mitri.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import mitri.task.Todo;

public class TaskListTest {
    @Test
    public void removeTest_indexWithinSize_noError() {
        mitri.list.TaskList list = new mitri.list.TaskList();
        list.add(new Todo("hello"));
        list.add(new Todo("hi"));
        list.remove(1);
        assert(list.size() == 1);
    }

    @Test
    public void removeTest_tooLargeIndex_exceptionThrown() {
        mitri.list.TaskList list = new mitri.list.TaskList();
        assertThrows(AssertionError.class, () -> list.remove(1));
    }

    @Test
    public void removeTest_tooSmallIndex_exceptionThrown() {
        mitri.list.TaskList list = new mitri.list.TaskList();
        list.add(new Todo("hello"));
        list.add(new Todo("hi"));
        assertThrows(AssertionError.class, () -> list.remove(-1));
    }

}
