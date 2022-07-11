
// attributed source: JsonSerializationDemo

package persistence;

import model.Task;
import model.TaskList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TaskList t = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTaskList() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyTaskList.json");
        try {
            TaskList t = reader.read();
            assertEquals("Your To-Do List", t.getName());
            assertEquals(0, t.numOfTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void checkTask(String name, String deadline, Boolean condition, Task t) {
        assertEquals(name, t.getName());
        assertEquals(deadline, t.getDeadline());
        assertEquals(condition, t.getCondition());
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralTaskList.json");
        try {
            TaskList t = reader.read();
            assertEquals("Your To-Do List", t.getName());
            ArrayList<Task> tasks = t.getAllTasks();
            assertEquals(2, tasks.size());
            checkTask("t1", "09/14/21", false, tasks.get(0));
            checkTask("t2", "02/10/20", true, tasks.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}