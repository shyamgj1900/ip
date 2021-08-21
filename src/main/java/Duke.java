import java.util.Objects;
import java.util.Scanner;

public class Duke {
    public static int listSize = 0;
    public static String[] textList = new String[100];

    public static void addList(String word) {
        if (Objects.equals(word, "list")) {
            System.out.println("-----------------------------------");
            for (int i = 0; i < listSize; i++) {
                System.out.println(i + 1 + ". " + textList[i]);
            }
            System.out.println("-----------------------------------");
        } else {
            textList[listSize] = word;
            System.out.println("-----------------------------------");
            System.out.println("added: " + textList[listSize]);
            System.out.println("-----------------------------------");
            listSize++;
        }
    }

    public static void main(String[] args) {
        Scanner myWord = new Scanner(System.in);

        System.out.println("-----------------------------------");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("-----------------------------------");

        String word = "";
        while (!Objects.equals(word, "bye")) {
            word = myWord.nextLine();
            if (!Objects.equals(word, "bye")) {
                addList(word);
            }
        }
        System.out.println("-----------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-----------------------------------");
    }
}
