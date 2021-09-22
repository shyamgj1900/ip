# Duke User Guide

Duke is a CLI(Command Line Interface) based application which allows users to manage tasks.

## How to get started

1. Make sure Java 11 is installed on your device
2. Download the Jar file from [here](https://github.com/shyamgj1900/ip/releases/tag/A-Release)
3. Copy the Jar file to a new folder, and run ```java -jar ip.jar```

Demo:
```markdown
Joe@MacBook-Pro Documents/jar % java -jar ip.jar

-----------------------------------
Hello! I'm Duke
What can I do for you?
-----------------------------------
---------------------------------------------------------------------------
File not found. Don't worry we have created a new text file for you.
---------------------------------------------------------------------------
-----------------------------------
Tasks file is currently empty.
-----------------------------------

```

## Commands

Action | Command
------ | -------
Add a deadline task | ```deadline TASK_DESCRIPTION /by DEADLINE_TIME```
Add an event task | ```event TASK_DESCRIPTION /at EVENT_TIME```
Add a todo task | ```todo TASK_DESCRIPTION```
List all tasks | ```list```
Mark task as done | ```done TASK_INDEX_NUMBER```
Find task | ```find TASK_NAME```
Delete a task | ```delete TASK_INDEX_NUMBER```
Help command | ```help```
Exit application | ```bye```

## Guide on command usage

### Add a deadline task 

A task of type deadline is added to the task list. 

Command: ```deadline TASK_DESCRIPTION /by DEADLINE_TIME```

**Note**
* User must specify the deadline time or else the task is not added.
* Deadline description should also not be empty.

Demo:

```
deadline submit CS assignment /by Monday 3PM
-----------------------------------
Got it. I've added this task
[D][ ] submit CS assignment  (by: Monday 3PM)
Now you have 1 task in the list
-----------------------------------
```

### Add an event task 

A task of type event is added to the task list. 

Command: ```event TASK_DESCRIPTION /at EVENT_TIME```

**Note**
* User must specify the event time or else the task is not added.
* Event description should also not be empty.

Demo:

```
event attend Kanye West concert /at Sunday 6PM
-----------------------------------
Got it. I've added this task
[E][ ] attend Kanye West concert  (at: Sunday 6PM)
Now you have 1 task in the list
-----------------------------------
```

### Add a todo task

A task of type todo is added to the task list. 

Command: ```todo TASK_DESCRIPTION```

**Note**
* Todo description should not be empty.

Demo:

```
todo buy groceries
-----------------------------------
Got it. I've added this task
[T][ ] buy groceries
Now you have 1 task in the list
-----------------------------------
```

### List all tasks

All the tasks in the task list are displayed on screen.

Command: ```list```

Demo:

```
list
-----------------------------------
1. [D][ ] submit CS assignment  (by: Monday 3PM)
2. [E][ ] attend Kanye West concert  (at: Sunday 6PM)
3. [T][ ] buy groceries
-----------------------------------
```

### Mark task as done

A particular task in the task list can be marked as done and is indicated by displaying a 'X' character in the task completion field.

Command: ```done TASK_INDEX_NUMBER```

**Note**
* Task completion field is empty by default.
* Index number must be specified.
* Index number cannot be greater than the total number of tasks in the list.

Demo:

```
done 1
-----------------------------------
Nice! I've marked this task as done:
[D][X] submit CS assignment  (by: Monday 3PM)
-----------------------------------
```

### Find task 

Task(s) in the task list can be found by matching the task description.

Command: ```find TASK_NAME```

**Note**
* If task name does not match any of the tasks in the list a ```No tasks match the given query``` message is displayed.

Demo:

```
find buy groceries
-----------------------------------
Here are the matching tasks in your list:
1. [T][ ] buy groceries
-----------------------------------
```

### Delete a task

A task in the task list can be deleted from it by specifying the index number of the task.

Command: ```delete TASK_INDEX_NUMBER```

**Note**
* Index number must be specified.
* Index number cannot be greater than the total number of tasks in the list.

Demo:

```
delete 2
-----------------------------------
Noted. I've removed this task:
[E][ ] attend Kanye West concert  (at: Sunday 6PM)
Now you have 2 tasks in the list
-----------------------------------
```

### Help command

All the commands are listed for reference along with examples on how to use them.

command: ```help```

Demo:

```
help
---------------------------------------------------------
1) Add a deadline task.
Example: deadline TASK_DESCRIPTION /by DEADLINE_TIME
2) Add an event task.
Example: event TASK_DESCRIPTION /at EVENT_TIME
3) Add a todo task.
Example: todo TASK_DESCRIPTION
4) List all tasks.
Example: list
5) Mark task as done.
Example: done TASK_INDEX_NUMBER
6) Find task in list.
Example: find TASK_NAME
7) Delete a task.
Example: delete TASK_INDEX_NUMBER
8) Exit application.
Example: bye
---------------------------------------------------------
```

### Exit application

The program is exited.

Command: ```bye```

Demo:

```
bye
-----------------------------------
Bye. Hope to see you again soon!
-----------------------------------
```

## File Storage

All the tasks entered by the users are stored on a text file and can be recovered when the application restarts. The tasks are updated in real time to the text file.

**Warning**
Users are not supposed to make any changes to the text file or else data may get corrupted.
