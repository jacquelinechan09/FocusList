package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonInterface;

public class TaskList implements JsonInterface {
    private ArrayList<Task> tasks;
    private String name;

    // EFFECTS: constructs TaskList with a name and empty list of tasks
    public TaskList(String name) {
        this.name = name;
        tasks = new ArrayList<>();
    }

    // EFFECTS: gets the name of a task list
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds the task into the list
    public void addTask(Task taskInList) {
        tasks.add(taskInList);
    }

    // MODIFIES: this
    // EFFECTS: removes a task from the list
    public void removeTask(Task taskInList) {
        tasks.remove(taskInList);
    }


    // REQUIRES: the task to remove must be ongoing
    // MODIFIES: this
    // EFFECTS: removes the ongoing task from the list
    public void removeOngoingTask(Task noLongerToDo) {
        if (noLongerToDo.getCondition() == false) {
            tasks.remove(noLongerToDo);
        }
    }

    // REQUIRES: the task to remove must be completed
    // MODIFIES: this
    // EFFECTS: removes the completed task from the list entirely
    public void removeCompletedTask(Task noLongerComplete) {
        if (noLongerComplete.getCondition() == true) {
            tasks.remove(noLongerComplete);
        }
    }

    // REQUIRES: the task must initially be ongoing
    // MODIFIES: this
    // EFFECTS: marks an ongoing task as completed
    public void finishTask(Task doneTask) {
        if (doneTask.getCondition() == false) {
            doneTask.reverseCondition();
        }
    }

    // REQUIRES: the task must initially be completed
    // MODIFIES: this
    // EFFECTS: marks the completed task as ongoing
    public void revertTask(Task doneTask) {
        if (doneTask.getCondition() == true) {
            doneTask.reverseCondition();
        }
    }

    // EFFECTS: return true if the task is in the list, false otherwise
    public boolean listContains(Task t) {
        if (tasks.contains(t)) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: return true if the task is completed
    public boolean completedContains(Task t) {
        if (t.getCondition() == true) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: see total number of tasks
    public int numOfTasks() {
        return tasks.size();
    }

    // EFFECTS: gets the entire list of tasks
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    // EFFECTS: gets the list of completed tasks
    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> empty = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getCondition() == true) {
                empty.add(t);
            }
        }
        return empty;
    }

    // EFFECTS: gets the list of ongoing tasks
    public ArrayList<Task> getOngoingTasks() {
        ArrayList<Task> empty = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getCondition() == false) {
                empty.add(t);
            }
        }
        return empty;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("TaskList", tasksToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : tasks) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}


