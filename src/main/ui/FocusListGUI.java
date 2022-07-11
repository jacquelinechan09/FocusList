package ui;

import model.Task;
import model.TaskList;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

// To-Do list GUI application
public class FocusListGUI extends JPanel implements ActionListener {
    private Task task;
    private TaskList taskList;
    private JTextField input;
    private JTextField inputTwo;
    private DefaultListModel<Task> someTasks;
    private String typedName;
    private String typedDate;
    private JFileChooser toSave;
    private ImageIcon icon;

    private static final String addTaskButton = "Add a task";
    private static final String savingButton = "Save task list to file";
    private static final String loadingButton = "Load task list from file";
    private static final String countButton = "See the number of tasks you have added to the list";
    private static final String trueButton = "True (completed)";
    private static final String falseButton = "False (ongoing)";

    // EFFECTS: constructs the FocusList application
    public FocusListGUI() {
        super(new BorderLayout());
        task = new Task("Sample Task", "01/29/21", true);
        taskList = new TaskList("Sample to-do list");
        someTasks = new DefaultListModel<>();
        input = new JTextField("**REPLACE THIS TEXT**", 20);
        input.addActionListener(this);
        inputTwo = new JTextField("**REPLACE THIS TEXT**", 20);
        toSave = new JFileChooser("./data");
        icon = new ImageIcon("./data/confetti.jpg");

        taskListGUI();
        selectionOptions();
    }

    // EFFECTS: sets the dimensions of the application
    private void taskListGUI() {
        JList<Task> tasksInApp = new JList<>(someTasks);
        tasksInApp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tasksInApp.setSelectedIndex(0);
        tasksInApp.setPreferredSize(new Dimension(400, 200));
        tasksInApp.setVisibleRowCount(30);
        JScrollPane listScrollPane = new JScrollPane(tasksInApp);
        listScrollPane.setName("Your Tasks:");
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        listScrollPane.setSize(400,200);
        add(listScrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: the home screen's button selections are created here
    private void selectionOptions() {
        JPanel buttonPane = new JPanel();
        Color lightGreen = new Color(229, 255, 204);
        buttonPane.setBackground(lightGreen);
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.PAGE_AXIS));
        JLabel title = new JLabel("FocusList - a place to keep track of your tasks");
        JLabel firstLineBreak = new JLabel("            ");
        JLabel lineBreak = new JLabel("            ");
        JLabel subtitle = new JLabel("Created by Jacqueline Chan");
        buttonPane.add(firstLineBreak);
        buttonPane.add(title);
        buttonPane.add(lineBreak);
        buttonPane.add(subtitle);
        buttonPane.add(Box.createHorizontalStrut(10));
        buttonPane.add(addToList());
        buttonPane.add(saveList());
        buttonPane.add(loadList());
        buttonPane.add(numberInList());
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(buttonPane, BorderLayout.EAST);
    }

    // EFFECTS: sets an action command/listener for the button counting the number of tasks added to the list
    private Component numberInList() {
        JButton countingTasks = new JButton(countButton);
        countingTasks.setActionCommand(countButton);
        countingTasks.addActionListener(e -> countAction());
        return countingTasks;
    }

    // EFFECTS: sets an action command/listener for the button adding a task to the list
    private Component addToList() {
        JButton addingTask = new JButton(addTaskButton);
        addingTask.setActionCommand(addTaskButton);
        addingTask.addActionListener(e -> interfaceDimensions());
        return addingTask;
    }

    // EFFECTS: sets an action command/listener for the button selecting that a task is complete (true)
    private Component selectTrue() {
        JButton trueTask = new JButton(trueButton);
        trueTask.setActionCommand(trueButton);
        trueTask.addActionListener(e -> constructTrue());
        return trueTask;
    }

    // EFFECTS: sets an action command/listener for the button selecting that a task is ongoing (false)
    private Component selectFalse() {
        JButton falseTask = new JButton(falseButton);
        falseTask.setActionCommand(falseButton);
        falseTask.addActionListener(e -> constructFalse());
        return falseTask;
    }

    // EFFECTS: creates the interface dimensions for the "New Task" JFrame
    private void interfaceDimensions() {
        JFrame constructTask = new JFrame("New Task");
        constructTask.getContentPane().add(taskName());
        constructTask.setSize(1200, 600);
        constructTask.setLocationRelativeTo(null);
        constructTask.setVisible(true);
        constructTask.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: creates a separate window to display the task count
    private void countAction() {
        JFrame countTasks = new JFrame("Number of Tasks");
        countTasks.getContentPane().add(sizeScreen());
        countTasks.setSize(1200, 600);
        countTasks.setLocationRelativeTo(null);
        countTasks.setVisible(true);
        countTasks.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: constructs the contents of the count tasks window, shows how many tasks were added to the list
    private Component sizeScreen() {
        int size = someTasks.getSize();
        JLabel jlabel = new JLabel("Your to-do list contains " + size + " tasks. ");
        return jlabel;
    }

    // EFFECTS: constructs a JFrame that displays after adding a completed task, sets a task's
    //          condition to be true
    private void constructTrue() {
        JFrame completedTask = new JFrame("Added your Completed Task");
        task.setFinish(true);
        completedTask.getContentPane().add(submitTrueScreen());
        completedTask.setSize(1200, 600);
        completedTask.setLocationRelativeTo(null);
        completedTask.setVisible(true);
        completedTask.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: constructs a JFrame that displays after adding an ongoing task, sets a task's
    //          condition to be false
    private void constructFalse() {
        JFrame completedTask = new JFrame("Added your Ongoing Task");
        task.setFinish(false);
        completedTask.getContentPane().add(submitFalseScreen());
        completedTask.setSize(1200, 600);
        completedTask.setLocationRelativeTo(null);
        completedTask.setVisible(true);
        completedTask.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: sets the dimensions of the screen after adding a completed task
    private void trueAction() {
        JFrame constructTask = new JFrame("Added a completed task");
        constructTask.getContentPane().add(submitTrueScreen());
        constructTask.setSize(1200, 600);
        constructTask.setLocationRelativeTo(null);
        constructTask.setVisible(true);
        constructTask.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    //EFFECTS: plays a sound when a task is added (when true or false is pressed)
    public void dingSound(String soundName) {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception exception) {
            System.out.println("The audio clip cannot be played at the moment.");
        }
    }


    // EFFECTS: displays the name and deadline of the saved completed task, along with a confetti image
    private Component submitTrueScreen() {
        JPanel trueScreen = new JPanel();
        typedName = input.getText();
        typedDate = inputTwo.getText();
        task = new Task(typedName, typedDate, true);
        taskList.addTask(task);
        someTasks.addElement(task);
        JLabel jlabel = new JLabel("This completed task has been saved: " + typedName + " with deadline "
                + typedDate);
        trueScreen.add(jlabel);
        Image image = icon.getImage();
        Image resized = image.getScaledInstance(800, 600,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(resized);
        dingSound("data/ding.wav");
        JLabel label = new JLabel(icon);
        trueScreen.add(label);
        return trueScreen;
    }

    // EFFECTS: sets the dimensions of the screen after adding an ongoing task
    private void falseAction() {
        JFrame constructTask = new JFrame("Added an ongoing task");
        constructTask.getContentPane().add(submitFalseScreen());
        constructTask.setSize(1200, 600);
        constructTask.setLocationRelativeTo(null);
        constructTask.setVisible(true);
        constructTask.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: displays the name and deadline of the saved ongoing task, along with a confetti image
    private Component submitFalseScreen() {
        JPanel falseScreen = new JPanel();
        typedName = input.getText();
        typedDate = inputTwo.getText();
        task = new Task(typedName, typedDate, false);
        taskList.addTask(task);
        someTasks.addElement(task);
        JLabel jlabel = new JLabel("This ongoing task has been saved: " + typedName + " with deadline "
                + typedDate);
        falseScreen.add(jlabel);
        Image image = icon.getImage();
        Image resized = image.getScaledInstance(800, 600,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(resized);
        dingSound("data/ding.wav");
        JLabel label = new JLabel(icon);
        falseScreen.add(label);
        return falseScreen;
    }

    // EFFECTS: sets up the textbox for typing in the task's name while adding a new task
    private Component taskName() {
        JPanel enterName = new JPanel();
        JLabel jlabel = new JLabel("Please type in the name of your task.");
        enterName.add(input);
        task.setName(typedName);
        enterName.add(jlabel);
        enterName.add(Box.createHorizontalStrut(10));
        enterName.add(new JSeparator(SwingConstants.VERTICAL));
        enterName.add(Box.createHorizontalStrut(10));
        enterName.add(typeDeadline());
        return enterName;
    }

    // EFFECTS: sets up the textbox for typing in the task's deadline when adding a new task
    private Component typeDeadline() {
        JPanel deadline = new JPanel();
        JLabel jlabel = new JLabel("Please type in the deadline of your task.");
        deadline.add(inputTwo);
        task.setDeadline(typedDate);
        deadline.add(jlabel);
        deadline.add(inputTwo);
        deadline.add(Box.createHorizontalStrut(10));
        deadline.add(new JSeparator(SwingConstants.VERTICAL));
        deadline.add(Box.createHorizontalStrut(10));
        deadline.add(ongoingOrComplete());
        return deadline;
    }

    // EFFECTS: sets up the true/false buttons for selecting whether a task is ongoing or complete
    private Component ongoingOrComplete() {
        JPanel conditionButton = new JPanel();
        JLabel jlabel = new JLabel("Please choose whether your task is completed (true or false)");
        conditionButton.add(jlabel);
        conditionButton.add(Box.createHorizontalStrut(10));
        conditionButton.add(new JSeparator(SwingConstants.VERTICAL));
        conditionButton.add(Box.createHorizontalStrut(10));
        conditionButton.add(trueButton());
        conditionButton.add(falseButton());
        return conditionButton;
    }

    // EFFECTS: constructs the true button when adding a task
    private Component trueButton() {
        JPanel truePanel = new JPanel();
        JButton pressTrue = new JButton(trueButton);
        pressTrue.setActionCommand(trueButton);
        pressTrue.addActionListener(e -> trueAction());
        JLabel trueLabel = new JLabel("Your completed task has been saved.");
        truePanel.add(trueLabel);
        pressTrue.add(Box.createHorizontalStrut(10));
        pressTrue.add(new JSeparator(SwingConstants.VERTICAL));
        pressTrue.add(Box.createHorizontalStrut(10));
        pressTrue.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(pressTrue, BorderLayout.EAST);
        pressTrue.add(selectTrue());
        return selectTrue();
    }

    // EFFECTS: constructs the false button when adding a task
    private Component falseButton() {
        JPanel falsePanel = new JPanel();
        JButton pressFalse = new JButton(falseButton);
        pressFalse.setActionCommand(falseButton);
        pressFalse.addActionListener(e -> falseAction());
        JLabel falseLabel = new JLabel("Your ongoing task has been saved.");
        falsePanel.add(falseLabel);
        pressFalse.add(Box.createHorizontalStrut(10));
        pressFalse.add(new JSeparator(SwingConstants.VERTICAL));
        pressFalse.add(Box.createHorizontalStrut(10));
        pressFalse.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(pressFalse, BorderLayout.EAST);
        pressFalse.add(selectFalse());
        return selectFalse();
    }

    // EFFECTS: constructs the button to save a list of tasks
    private Component saveList() {
        JButton saveButton = new JButton(savingButton);
        saveButton.setActionCommand(savingButton);
        saveButton.addActionListener(e -> savingTheTasks());
        return saveButton;
    }

    // EFFECTS: allows the user to save their list of tasks to file
    private void savingTheTasks() {
        JFrame savingFrame = new JFrame();
        toSave.setDialogTitle("Specify a file to save");
        int userSelection = toSave.showSaveDialog(savingFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = toSave.getSelectedFile();
            JsonWriter saving = new JsonWriter(fileToSave.getAbsolutePath());
            try {
                saving.open();
                saving.write(taskList);
                saving.close();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // EFFECTS: constructs the button to load a list of tasks
    private Component loadList() {
        JButton loadButton = new JButton(loadingButton);
        loadButton.setActionCommand(loadingButton);
        loadButton.addActionListener(e -> loadingTheTasks());
        return loadButton;
    }

    // EFFECTS: allows the user to load their list of tasks from file
    private void loadingTheTasks() {
        toSave.setDialogTitle("Specify a file to load");
        toSave.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = toSave.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = toSave.getSelectedFile();
            JsonReader reader = new JsonReader(selectedFile.getAbsolutePath());
            try {
                taskList = reader.read();
                someTasks.clear();
                for (Task task : taskList.getAllTasks()) {
                    someTasks.addElement(task);
                }
                System.out.println("Loaded " + taskList.getName() + " from " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + selectedFile.getAbsolutePath());
            }
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }

    // EFFECTS: creates the GUI for the to-do list application
    private static void makeGUI() {
        JFrame frame = new JFrame("FocusList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new FocusListGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(FocusListGUI::makeGUI);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
