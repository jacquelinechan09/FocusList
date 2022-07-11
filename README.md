# FocusList

## Stay on top of your commitments.

![image](https://user-images.githubusercontent.com/109058047/178197250-6cf56872-4dec-4a25-9535-0558fd1057d1.png)

![image](https://user-images.githubusercontent.com/109058047/178197504-15030ea4-280d-483c-be73-e9ccb72777b5.png)

This application consists of items to do. The to-do list has the following features:
- Input a name, deadline, and condition for each of your tasks
- See the number of tasks you have
- Tasks can be added and deleted
- There is an option to display either only ongoing or completed items, or both
- Save your tasks to file
- An option to load your saved tasks from file

Anyone can use this application to stay organized with their tasks. This project is of interest to me because I personally find that having a structured list of my to-do’s helps me to stay productive and on track with my work

## User Stories

- As a user, I want to be able to add an item to my to-do list
- As a user, I want to be able to delete items from my to-do list
- As a user, I want to be able to see the number of tasks I have
- As a user, I want to be able to see the ongoing and completed list items both separately and together
- As a user, I want to be able to include a name, deadline, and condition for every task
- As a user, I want to be able to save my tasks to a file with their name, deadline, and condition.
- As a user, I want to be able to load my saved tasks
- As a user, when I re-run the application, I want the option to access my to-do list from file
- Phase 4: Task 2 - Make appropriate use of a bi-directional association (classes in model: Category and Task).  These two classes both call methods on the other class. For example, addTaskToCategory(Task task) in the Category class calls the Task class's categorize(Category category) method.
- Phase 4: Task 3 - Reflections: 
    - could refactor my FocusListGUI methods (and methods in other classes such as TaskList as well) to reduce coupling and increase cohesion/single responsibility principle. 
    - Might also make it such that there is a UML class relationship between Category and TaskList (a TaskList can then contain 0..* categories, or an arbitrary number of categories)
