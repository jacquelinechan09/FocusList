
// attributed source: JsonSerializationDemo

package persistence;

import model.Task;
import model.TaskList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {


    @Test
    void testWriterInvalidFile() {
        try {
            TaskList t = new TaskList("Your To-Do List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTaskList() {
        try {
            TaskList t = new TaskList("Your To-Do List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTaskList.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTaskList.json");
            t = reader.read();
            assertEquals("Your To-Do List", t.getName());
            assertEquals(0, t.numOfTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    private void checkTask(String name, String deadline, Boolean condition, Task t) {
        assertEquals(name, t.getName());
        assertEquals(deadline, t.getDeadline());
        assertEquals(condition, t.getCondition());
    }

    @Test
    void testWriterGeneralTaskList() {
        try {
            TaskList t = new TaskList("Your To-Do List");
            t.addTask(new Task("t1", "09/14/21", false));
            t.addTask(new Task("t2", "02/10/20", true));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTaskList.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTaskList.json");
            t = reader.read();
            assertEquals("Your To-Do List", t.getName());
            ArrayList<Task> tasks = t.getAllTasks();
            assertEquals(2, tasks.size());
            checkTask("t1", "09/14/21", false, tasks.get(0));
            checkTask("t2", "02/10/20", true, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}