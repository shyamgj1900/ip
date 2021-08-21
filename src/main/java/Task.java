import java.util.Objects;

public class Task {
    private static int listSize = 0;
    private static String[] textList = new String[100];
    private static boolean[] isDone = new boolean[100];

    public static String getStatusIcon(int i) {
        return (isDone[i] ? "X" : " ");
    }

    public static void addList(String word) {
        if (Objects.equals(word, "list")) {
            System.out.println("-----------------------------------");
            for (int i = 0; i < listSize; i++) {
                System.out.println(i + 1 + ". " + "[" + getStatusIcon(i) + "] " + textList[i]);
            }
            System.out.println("-----------------------------------");
        } else if (word.contains("done")) {
            String l = word.substring(word.length() - 1);
            int idx = Integer.parseInt(l);
            isDone[idx - 1] = true;
            System.out.println("-----------------------------------");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("[" + getStatusIcon(idx - 1) + "] " + textList[idx - 1]);
            System.out.println("-----------------------------------");
        } else {
            textList[listSize] = word;
            isDone[listSize] = false;
            System.out.println("-----------------------------------");
            System.out.println("added: " + textList[listSize]);
            System.out.println("-----------------------------------");
            listSize++;
        }
    }
}
