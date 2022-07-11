package model;

import java.util.HashSet;
import java.util.Set;

// Represents a category for tasks having a name and upper limit of tasks it can contain
public class Category {
    private Set<Task> tasks;
    private int maxLimit;
    private String name;

    // EFFECTS: constructs a category with name and maximum limit of tasks
    public Category(String name, int maxLimit) {
        this.name = name;
        this.maxLimit = maxLimit;
        this.tasks = new HashSet<Task>();
    }

    // EFFECTS: returns the name of this category
    public String getNameOfCategory() {
        return this.name;
    }

    // EFFECTS: gets the maximum limit of tasks that can go into this category
    public int getMaxLimit() {
        return this.maxLimit;
    }

    // EFFECTS: returns a set of tasks placed in this category
    public Set<Task> getTasksInCategory() {
        return this.tasks;
    }

    // EFFECTS: returns true if this category has reached the max number of tasks in it, false otherwise
    public boolean limitReached() {
        if (tasks.size() < maxLimit) {
            return false;
        }
        return true;
    }

    // REQUIRES: !limitReached()
    // MODIFIES: this, task
    // EFFECTS: adds task to this category
    public void addTaskToCategory(Task task) {
        if (!this.tasks.contains(task)) {
            this.tasks.add(task);
            task.categorize(this);
        }
    }

    // MODIFIES: this, task
    // EFFECTS: removes task from this category
    public void removeTask(Task task) {
        if (this.tasks.contains(task)) {
            this.tasks.remove(task);
        }
    }
}
