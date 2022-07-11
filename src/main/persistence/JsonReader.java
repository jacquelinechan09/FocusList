
// attributed source: JsonSerializationDemo

package persistence;

import model.Task;
import model.TaskList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TaskList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TaskList from JSON object and returns it
    private TaskList parseList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TaskList t = new TaskList(name);
        addTasks(t, jsonObject);
        return t;
    }

    // MODIFIES: t
    // EFFECTS: parses tasks from JSON object and adds them to TaskList
    private void addTasks(TaskList t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("TaskList");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addATask(t, nextTask);
        }
    }

    // MODIFIES: t
    // EFFECTS: parses task from JSON object and adds it to TaskList
    private void addATask(TaskList t, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String deadline = jsonObject.getString("deadline");
        boolean condition = jsonObject.getBoolean("condition");
        Task task = new Task(name, deadline, condition);
        t.addTask(task);
    }
}
