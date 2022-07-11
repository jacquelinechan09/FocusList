package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    private TaskList listOfTasks;
    private TaskList emptyList;

    Task task;
    Task otherTask;
    Task yetAnotherTask;

    @BeforeEach
    public void runBefore() {
        task = new Task("test", "12/01/21", false);
        listOfTasks = new TaskList("");
        task.createDeadline("12/01/21");
        task.createCondition(false);
        listOfTasks.addTask(task);

        yetAnotherTask = new Task("yet another task", "08/11/20", true);
        yetAnotherTask.createDeadline("08/11/20");
        yetAnotherTask.createCondition(true);
        listOfTasks.addTask(yetAnotherTask);

        emptyList = new TaskList("");
    }

    @Test
    public void testAddOngoingTask() {
        assertTrue(listOfTasks.listContains(task));
    }

    @Test
    public void testRemoveOngoingTask() {
        assertTrue(listOfTasks.listContains(task));
        listOfTasks.removeOngoingTask(task);
        assertFalse(listOfTasks.listContains(task));
        listOfTasks.removeOngoingTask(yetAnotherTask);
        assertTrue(listOfTasks.listContains(yetAnotherTask));
    }

    @Test
    public void testRemoveCompletedTask() {
        listOfTasks.removeCompletedTask(yetAnotherTask);
        assertFalse(listOfTasks.listContains(yetAnotherTask));
        listOfTasks.removeCompletedTask(task);
        assertTrue(listOfTasks.listContains(task));
    }

    @Test
    public void testFinishTask() {
        assertTrue(listOfTasks.listContains(task));
        assertEquals(false, task.getCondition());
        listOfTasks.finishTask(task);
        assertEquals(true, task.getCondition());
        listOfTasks.finishTask(yetAnotherTask);
        assertEquals(true, yetAnotherTask.getCondition());
    }

    @Test
    public void testOngoingContains() {
        otherTask = new Task("another test", "12/01/22", false);
        listOfTasks.addTask(otherTask);
        assertTrue(listOfTasks.listContains(otherTask));
    }

    @Test
    public void testCompletedContains() {
        listOfTasks.finishTask(task);
        assertTrue(listOfTasks.completedContains(task));
        listOfTasks.revertTask(task);
        assertFalse(listOfTasks.completedContains(task));
    }

    @Test
    public void testRevertTask() {
        assertTrue(listOfTasks.listContains(yetAnotherTask));
        assertEquals(true, yetAnotherTask.getCondition());
        listOfTasks.revertTask(yetAnotherTask);
        assertEquals(false, yetAnotherTask.getCondition());
        listOfTasks.revertTask(task);
        assertEquals(false, task.getCondition());
    }

    @Test
    public void testGetCompletedTasks() {
        emptyList.addTask(yetAnotherTask);
        assertEquals(listOfTasks.getCompletedTasks(), emptyList.getAllTasks());
    }

    @Test
    public void testGetOngoingTasks() {
        emptyList.addTask(task);
        assertEquals(listOfTasks.getOngoingTasks(), emptyList.getAllTasks());
    }

    @Test
    public void testRemoveTask() {
        assertTrue(listOfTasks.listContains(yetAnotherTask));
        listOfTasks.removeTask(yetAnotherTask);
        assertFalse(listOfTasks.listContains(yetAnotherTask));
    }

    @Test
    public void testGetAllTasks() {
        listOfTasks.getAllTasks();
        emptyList.addTask(task);
        emptyList.addTask(yetAnotherTask);
        assertEquals(listOfTasks.getAllTasks(), emptyList.getAllTasks());
    }


}
