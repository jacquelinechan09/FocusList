package model;

import org.json.JSONObject;
import persistence.JsonInterface;

public class Task implements JsonInterface {
    private String name;   //task name
    private String deadline;  //when the task is due, e.g. task due on February 15, 2021 is "02/15/21"
    private boolean condition; // false if ongoing, true if completed
    private Category category;

    // MODIFIES: this
    // EFFECTS: makes a new task
    public Task(String name, String deadline, Boolean condition) {
        this.name = name;
        this.deadline = deadline;
        this.condition = condition;
    }

    // EFFECTS: gets the category in which is a task is contained
    public Category getCategoryOfTask() {
        return this.category;
    }

    // EFFECTS: returns true if a task is inside of a category, false otherwise
    public boolean taskInsideCategory() {
        if (this.category != null) {
            return true;
        }
        return false;
    }

    // REQUIRES: !category.limitReached()
    // MODIFIES: this, category
    // EFFECTS: places task into a category
    public void categorize(Category category) {
        if (this.category != category) {
            this.category = category;
            category.addTaskToCategory(this);
        }
    }

    // MODIFIES: this, Category c = getCategoryOfTask()
    // EFFECTS: if task is placed inside of a category, removes task from that category, otherwise do nothing
    public void removeTaskFromCategory() {
        if (this.taskInsideCategory()) {
            this.category = null;
        }
    }

    // EFFECTS: creates a deadline for a task
    public void createDeadline(String deadline) {
        this.deadline = deadline;
    }

    // EFFECTS: creates a condition for a task
    public void createCondition(Boolean condition) {
        this.condition = condition;
    }

    // EFFECTS: returns the condition (false if ongoing, true if completed) of a task
    public boolean getCondition() {
        return condition;
    }

    // MODIFIES: this
    // EFFECTS: reverses the condition of a task
    public void reverseCondition() {
        this.condition = !condition;
    }

    // EFFECTS: gets the name of a task
    public String getName() {
        return name;
    }

    // EFFECTS: sets the name of a task
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Task{"
                + "name='" + name + '\''
                + ", deadline='" + deadline + '\''
                + ", condition=" + condition
                + '}';
    }

    // EFFECTS: sets the deadline of a task
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    // EFFECTS: gets the deadline of a task
    public String getDeadline() {
        return deadline;
    }

    // EFFECTS: sets the condition of a task
    public void setFinish(boolean condition) {
        this.condition = condition;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("deadline", deadline);
        json.put("condition", condition);
        return json;
    }
}

