import java.util.Objects;
import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        Scanner myWord = new Scanner(System.in);

        System.out.println("-----------------------------------");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("-----------------------------------");

        String word = "";
        while (!Objects.equals(word, "bye")) {
            word = myWord.nextLine();
            if (!Objects.equals(word, "bye")) {
                Task t = new Task();
                t.addList(word);
            }
        }
        System.out.println("-----------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-----------------------------------");
    }
}
