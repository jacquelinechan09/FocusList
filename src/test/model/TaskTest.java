package model;

import model.Task;
import model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskTest {
    private Category categoryA;
    private Category categoryB;
    private Task taskA;
    private Task taskUrgent;
    private Task taskB;
    private Task taskNotUrgent;

    private Task task;
    public String name = "Exercise for 30 minutes every day";
    public String deadline = "09/17/21";
    public Boolean condition = false;


    @BeforeEach
    public void runBefore() {
        categoryA = new Category("Urgent", 2);
        categoryB = new Category("Less urgent", 3);
        taskA = new Task("Study for exam today", "04/17/21", false);
        taskUrgent = new Task("Apply for jobs", "04/12/21", false);
        taskB = new Task("Clean my room", "09/01/22", false);
        taskNotUrgent = new Task("Go for a walk", "12/31/21", false);

        task = new Task(name, "", false);
        task.createDeadline(deadline);
        task.createCondition(false);
    }

    @Test
    public void testConstructor() {
        assertNull(task.getCategoryOfTask());
        assertEquals(name, task.getName());
        assertEquals(deadline, task.getDeadline());
        assertEquals(condition, task.getCondition());
    }

    @Test
    public void testCreateDeadline() {
        task.createDeadline("04/02/22");
        assertEquals("04/02/22", task.getDeadline());
    }

    @Test
    public void testCreateCondition() {
        Task sample = new Task("sample item", "08/27/19", true);
        sample.createCondition(true);
        assertEquals(true, sample.getCondition());
    }

    @Test
    public void testReverseCondition() {
        Task sample = new Task("sample item", "02/03/15", true);
        sample.createCondition(true);
        assertEquals(true, sample.getCondition());
        sample.reverseCondition();
        assertEquals(false, sample.getCondition());
    }

    @Test
    public void testSetName() {
        task.setName("study for CS midterm");
        assertEquals("study for CS midterm", task.getName());
    }

    @Test
    public void testSetDeadline() {
        task.setDeadline("12/19/21");
        assertEquals("12/19/21", task.getDeadline());
    }

    @Test
    public void testSetFinish() {
        task.setFinish(true);
        assertEquals(true, task.getCondition());
        task.setFinish(false);
        assertEquals(false, task.getCondition());
    }

    @Test
    void testCategorize() {
        assertFalse(taskA.taskInsideCategory());
        taskA.categorize(categoryA);
        assertTrue(taskA.taskInsideCategory());
    }

    @Test
    void testRemoveFromCategory() {
        taskA.categorize(categoryA);
        assertTrue(taskA.taskInsideCategory());
        taskA.removeTaskFromCategory();
        assertFalse(taskA.taskInsideCategory());
    }

    @Test
    void testChangeBus() {
        taskA.categorize(categoryA);
        assertEquals(categoryA, taskA.getCategoryOfTask());
        taskA.categorize(categoryB);
        assertEquals(categoryB, taskA.getCategoryOfTask());
    }

}

