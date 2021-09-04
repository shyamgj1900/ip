import java.util.Objects;
import java.util.Scanner;

public class Duke {
    public static final int MAX_SIZE = 100;

    public static void main(String[] args) {
        Scanner mySentence = new Scanner(System.in);
        Task[] task = new Task[MAX_SIZE];
        int totalTask = 0;
        welcomeMessage();
        String sentence = "";
        while (!Objects.equals(sentence, "bye")) {
            try {
                sentence = mySentence.nextLine();
                if (Objects.equals(sentence, "list")) {
                    printList(task, totalTask);
                } else if (sentence.contains("done")) {
                    markDone(task, totalTask, sentence);
                } else if (Objects.equals(sentence, "bye")) {
                    exitMessage();
                } else {
                    totalTask = createTask(task, totalTask, sentence);
                }
            } catch (DukeException err) {
                System.out.println("---------------------------------------------------------");
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println("---------------------------------------------------------");
            } catch (IndexOutOfBoundsException err) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println(err.getMessage());
                System.out.println("---------------------------------------------------------------------------");
            } catch (NullPointerException err) {
                System.out.println("---------------------------------------------------------");
                System.out.println(err.getMessage());
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    private static int createTask(Task[] task, int totalTask, String sentence) throws DukeException, IndexOutOfBoundsException {
        if (sentence.contains("deadline")) {
            sentence = sentence.replace("deadline", "");
            int slashPos = sentence.indexOf("/");
            if(slashPos < 0) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of a deadline cannot be empty. Must also specify /by");
            }
            String deadline = sentence.substring(0, slashPos);
            String by = null;
            int byPos = sentence.indexOf("/by");
            by = sentence.substring(byPos + 3);
            task[totalTask] = new Deadline(deadline, by);
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(task[totalTask].toString());
            System.out.println("Now you have " + (totalTask + 1) + " tasks in the list");
            System.out.println("-----------------------------------");
            totalTask++;
        } else if (sentence.contains("event")) {
            sentence = sentence.replace("event", "");
            int slashPos = sentence.indexOf("/");
            if(slashPos < 0) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of an event cannot be empty. Must also specify /at");
            }
            String event = sentence.substring(0, slashPos);
            String at = null;
            int atPos = sentence.indexOf("/at");
            at = sentence.substring(atPos + 3);
            task[totalTask] = new Event(event, at);
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(task[totalTask].toString());
            System.out.println("Now you have " + (totalTask + 1) + " tasks in the list");
            System.out.println("-----------------------------------");
            totalTask++;
        } else if (sentence.contains("todo")) {
            String todo = sentence.replace("todo", "");
            if(todo.equals("")) {
                throw new IndexOutOfBoundsException("OOPS!!! The description of a todo cannot be empty.");
            }
            task[totalTask] = new Todo(todo);
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(task[totalTask].toString());
            System.out.println("Now you have " + (totalTask + 1) + " tasks in the list");
            System.out.println("-----------------------------------");
            totalTask++;
        } else {
            throw new DukeException();
        }
        return totalTask;
    }

    private static void markDone(Task[] task, int totalTask, String word) throws NullPointerException {
        String num = word.substring(word.length() - 1);
        int index = Integer.parseInt(num);
        if(index > totalTask) {
            throw new NullPointerException("Number given is more than the number of tasks in list");
        }
        task[index - 1].setDone();
        System.out.println("-----------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task[index - 1].toString());
        System.out.println("-----------------------------------");
    }

    private static void printList(Task[] task, int totalTask) {
        System.out.println("-----------------------------------");
        if(totalTask == 0) {
            System.out.println("No tasks in list");
        }
        else {
            for (int i = 0; i < totalTask; i++) {
                System.out.println(i + 1 + ". " + task[i].toString());
            }
        }
        System.out.println("-----------------------------------");
    }

    private static void welcomeMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("-----------------------------------");
    }

    private static void exitMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-----------------------------------");
    }
}
