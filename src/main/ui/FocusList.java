package ui;


import model.Task;
import model.TaskList;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;
import java.util.List;

// To-Do list application
public class FocusList {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static Task task = new Task("Sample Task", "02/17/21", true);
    private static TaskList taskList = new TaskList("List of Tasks");
    private static final String JSON_STORAGE = "./data/focuslist.json";
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs list and runs the application
    public FocusList() throws FileNotFoundException {
        taskList = new TaskList("");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
        runList();
    }

    // MODIFIES: this
    // EFFECTS: processes input by user
    public void runList() {
        boolean stillRun = true;
        String command = null;

        init();

        String typedNote;
        String typedDate;
        Boolean typedCondition;

        while (stillRun) {
            showOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                stillRun = false;
                break;

            } else {
                doCommand(command);
            }

        }

        System.out.println("Bye for now!");
    }

    // MODIFIES: this
    // EFFECTS: does the user command
    private void doCommand(String command) {
        if (command.equals("a")) {
            addingInput();
        } else if (command.equals("v")) {
            printAllTasks();
        } else if (command.equals("o")) {
            printOngoingTasks();
        } else if (command.equals("c")) {
            printCompletedTasks();
        } else if (command.equals("r")) {
            System.out.println("Your most recently added task has been deleted. ");
            taskList.removeTask(task);
        } else if (command.equals("q")) {
            bye();
        } else if (command.equals("s")) {
            numOfTasks();
        } else if (command.equals("f")) {
            saveList();
        } else if (command.equals("l")) {
            loadList();
        } else {
            System.out.println("Please choose a valid selection.");
        }
    }

    // EFFECTS: see total number of tasks
    public void numOfTasks() {
        List<Task> toDos = taskList.getAllTasks();
        System.out.println("The number of tasks you have is: " + toDos.size());
    }

    // EFFECTS: exits the program
    public void bye() {
        System.out.println("Bye for now!");
        System.exit(0);
    }

    // EFFECTS: saves the workroom to file
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(taskList);
            jsonWriter.close();
            System.out.println("Saved " + taskList.getName() + " to " + JSON_STORAGE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadList() {
        try {
            taskList = jsonReader.read();
            System.out.println("Loaded " + taskList.getName() + " from " + JSON_STORAGE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    // EFFECTS: prints all tasks into the ui
    public void printAllTasks() {
        List<Task> toDos = taskList.getAllTasks();
        System.out.println("Here are all of your tasks: ");
        for (Task task : toDos) {
            System.out.println("Name: " + task.getName() + ", deadline: " + task.getDeadline()
                    + ", condition: " + task.getCondition());
        }
    }

    // EFFECTS: prints ongoing tasks into the ui
    public void printOngoingTasks() {
        List<Task> toDos = taskList.getOngoingTasks();
        System.out.println("Here are all of your ongoing tasks: ");
        for (Task task : toDos) {
            System.out.println("Name: " + task.getName() + ", deadline: " + task.getDeadline()
                    + ", condition: " + task.getCondition());
        }
    }

    // EFFECTS: prints completed tasks into the ui
    public void printCompletedTasks() {
        List<Task> toDos = taskList.getCompletedTasks();
        System.out.println("Here are all of your completed tasks: ");
        for (Task task : toDos) {
            System.out.println("Name: " + task.getName() + ", deadline: " + task.getDeadline()
                    + ", condition: " + task.getCondition());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a task by taking in user input
    public void addingInput() {
        task = new Task(null, "", false);
        System.out.println("Please type your note here:");
        String typedNote = input.next();
        task.setName(typedNote);
        System.out.println("Your note is: " + typedNote);
        System.out.println("Please enter the task's deadline in the format MM/DD/YY ");
        String typedDate = input.next();
        task.setDeadline(typedDate);
        System.out.println("Your due date is: " + typedDate);
        System.out.println("Is the task ongoing or completed? Please enter true for completed, "
                + "and false otherwise. ");
        boolean typedCondition = input.nextBoolean();
        task.setFinish(typedCondition);
        System.out.println("Your task's condition is: " + typedCondition);
        System.out.println("Your task is " + typedNote + " with deadline " + typedDate
                + " and condition " + typedCondition);
        task.setFinish(typedCondition);
        taskList.addTask(task);
        logTasks(typedNote, typedDate, typedCondition);

    }

    boolean ongoing = false;
    boolean completed = true;
    String typedCondition1 = new Boolean(ongoing).toString();
    String typedCondition2 = new Boolean(completed).toString();

    // MODIFIES: this
    // EFFECTS: concats the note, deadline, and condition fields of a task
    private void logTasks(String typedNote, String typedDate, Boolean typedCondition) {
        TaskList taskList = new TaskList("");
        if (typedCondition == false) {
            typedNote.concat(typedDate.concat(typedCondition1));
        } else {
            typedNote.concat((typedDate.concat(typedCondition2)));
        }
        taskList.addTask(task);
    }

    // MODIFIES: this
    // EFFECTS: initializes program
    private void init() {
        task = new Task(null, "", false);
        input = new Scanner(System.in);
    }

    // EFFECTS: displays options for user to choose from
    private void showOptions() {
        System.out.println("\nChoose from:");
        System.out.println("\ta -> Add new task");
        System.out.println("\tv -> View all tasks");
        System.out.println("\to -> View ongoing tasks");
        System.out.println("\tc -> View completed tasks");
        System.out.println("\tr -> Remove most recently added task");
        System.out.println("\ts -> See the total number of tasks you have");
        System.out.println("\tf -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> Quit program");
    }


}