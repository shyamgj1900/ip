import java.util.Objects;
import java.util.Scanner;

public class Duke {
    public static final int MAX_SIZE = 100;

    public static void main(String[] args) {
        Scanner mySentence = new Scanner(System.in);
        Task[] t = new Task[MAX_SIZE];
        int ctr = 0;
        welcomeMessage();
        String sentence = "";
        while (!Objects.equals(sentence, "bye")) {
            sentence = mySentence.nextLine();
            if (Objects.equals(sentence, "list")) {
                printList(t, ctr);
            } else if (sentence.contains("done")) {
                markDone(t, sentence);
            } else if (Objects.equals(sentence, "bye")) {
                exitMessage();
            } else {
                ctr = createTask(t, ctr, sentence);
            }
        }
    }

    private static int createTask(Task[] t, int ctr, String sentence) {
        if (sentence.contains("deadline")) {
            sentence = sentence.replace("deadline", "");
            int slashPos = sentence.indexOf("/");
            String task = sentence.substring(0, slashPos);
            String by = "";
            int byPos = sentence.indexOf("/by");
            by = sentence.substring(byPos + 3);
            t[ctr] = new Deadline(task, by);
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(t[ctr].toString());
            System.out.println("Now you have " + (ctr + 1) + " tasks in the list");
            System.out.println("-----------------------------------");
            ctr++;
        } else if (sentence.contains("event")) {
            sentence = sentence.replace("event", "");
            int slashPos = sentence.indexOf("/");
            String task = sentence.substring(0, slashPos);
            String at = "";
            int atPos = sentence.indexOf("/at");
            at = sentence.substring(atPos + 3);
            t[ctr] = new Event(task, at);
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(t[ctr].toString());
            System.out.println("Now you have " + (ctr + 1) + " tasks in the list");
            System.out.println("-----------------------------------");
            ctr++;
        } else if (sentence.contains("todo")) {
            String task = sentence.replace("todo", "");
            t[ctr] = new Todo(task);
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task");
            System.out.println(t[ctr].toString());
            System.out.println("Now you have " + (ctr + 1) + " tasks in the list");
            System.out.println("-----------------------------------");
            ctr++;
        }
        return ctr;
    }

    private static void markDone(Task[] t, String word) {
        String l = word.substring(word.length() - 1);
        int idx = Integer.parseInt(l);
        t[idx - 1].setDone();
        System.out.println("-----------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t[idx - 1].toString());
        System.out.println("-----------------------------------");
    }

    private static void printList(Task[] t, int ctr) {
        System.out.println("-----------------------------------");
        for (int i = 0; i < ctr; i++) {
            System.out.println(i + 1 + ". " + t[i].toString());
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
