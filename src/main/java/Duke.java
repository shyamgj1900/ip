import java.util.Objects;
import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        Scanner myWord = new Scanner(System.in);
        Task[] t = new Task[100];
        int ctr = 0;
        welcomeMessage();
        String word = "";
        while (!Objects.equals(word, "bye")) {
            word = myWord.nextLine();
            if (Objects.equals(word, "list")) {
                printList(t, ctr);
            } else if (word.contains("done")) {
                markDone(t, word);
            } else if (Objects.equals(word, "bye")) {
                exitMessage();
            } else {
                ctr = createTask(t, ctr, word);
            }
        }
    }

    private static int createTask(Task[] t, int ctr, String word) {
        t[ctr] = new Task(word);
        System.out.println("-----------------------------------");
        System.out.println("added: " + t[ctr].getTask());
        System.out.println("-----------------------------------");
        ctr++;
        return ctr;
    }

    private static void markDone(Task[] t, String word) {
        String l = word.substring(word.length() - 1);
        int idx = Integer.parseInt(l);
        t[idx - 1].setDone();
        System.out.println("-----------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + t[idx - 1].getStatusIcon() + "] " + t[idx - 1].getTask());
        System.out.println("-----------------------------------");
    }

    private static void printList(Task[] t, int ctr) {
        System.out.println("-----------------------------------");
        for (int i = 0; i < ctr; i++) {
            System.out.println(i + 1 + ". " + "[" + t[i].getStatusIcon() + "] " + t[i].getTask());
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
