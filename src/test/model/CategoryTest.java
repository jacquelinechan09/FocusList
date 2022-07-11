package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private Category categoryA;
    private Category categoryB;
    private Task taskA;
    private Task taskUrgent;
    private Task taskB;
    private Task taskNotUrgent;

    @BeforeEach
    void runBefore() {
        categoryA = new Category("Urgent", 2);
        categoryB = new Category("Less urgent", 3);
        taskA = new Task("Study for exam today", "04/17/21", false);
        taskUrgent = new Task("Apply for jobs", "04/12/21", false);
        taskB = new Task("Clean my room", "09/01/22", false);
        taskNotUrgent = new Task("Go for a walk", "12/31/21", false);
    }

    @Test
    void testConstructor() {
        assertEquals("Urgent", categoryA.getNameOfCategory());
        assertEquals(2, categoryA.getMaxLimit());
    }

    @Test
    void testEmptyCategory() {
        assertEquals(0, categoryB.getTasksInCategory().size());
    }

    @Test
    void testAddTasksNoDuplicates() {
        categoryA.addTaskToCategory(taskA);
        assertEquals(1, categoryA.getTasksInCategory().size());
        categoryA.addTaskToCategory(taskUrgent);
        assertEquals(2, categoryA.getTasksInCategory().size());
    }

    @Test
    void testAddStudentsWithDuplicates() {
        categoryA.addTaskToCategory(taskA);
        assertEquals(1, categoryA.getTasksInCategory().size());
        categoryA.addTaskToCategory(taskA);
        assertEquals(1, categoryA.getTasksInCategory().size());
    }

    @Test
    void testCategoryFull() {
        assertFalse(categoryA.limitReached());
        categoryA.addTaskToCategory(taskA);
        assertFalse(categoryA.limitReached());
        categoryA.addTaskToCategory(taskUrgent);
        assertTrue(categoryA.limitReached());
    }

    @Test
    void testRemoveTasks() {
        categoryB.addTaskToCategory(taskB);
        categoryB.addTaskToCategory(taskNotUrgent);
        assertEquals(2, categoryB.getTasksInCategory().size());
        categoryB.removeTask(taskB);
        assertEquals(1, categoryB.getTasksInCategory().size());
        categoryB.removeTask(taskNotUrgent);
        assertEquals(0, categoryB.getTasksInCategory().size());
    }

    @Test
    void testCategoryChanged() {
        categoryA.addTaskToCategory(taskA);
        assertTrue(categoryA.getTasksInCategory().contains(taskA));
        categoryB.addTaskToCategory(taskA);
        assertTrue(categoryB.getTasksInCategory().contains(taskA));
    }

}