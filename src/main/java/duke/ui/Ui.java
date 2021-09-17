package duke.ui;

import java.util.Scanner;

public class Ui {
    private String command = "";

    public void readCommand() {
        Scanner myCommand = new Scanner(System.in);
        command = myCommand.nextLine();
    }

    public String getCommand() {
        return this.command;
    }

    public void welcomeMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("-----------------------------------");
    }

    public void exitMessage() {
        System.out.println("-----------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-----------------------------------");
    }

    public void showShortLine() {
        System.out.println("-----------------------------------");
    }

    public void showMediumLine() {
        System.out.println("---------------------------------------------------------");
    }

    public void showLongLine() {
        System.out.println("---------------------------------------------------------------------------");
    }

    public void fileNotFoundMessage() {
        System.out.println("File not found");
    }

    public void taskLoadMessage() {
        System.out.println("Tasks loaded from file!!!!");
    }

    public void fileEmptyMessage() {
        System.out.println("Tasks file empty");
    }

    public void invalidCommandMessage() {
        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    public void taskAddedMessage() {
        System.out.println("Got it. I've added this task");
    }

    public void taskRemovedMessage() {
        System.out.println("Noted. I've removed this task:");
    }

    public void taskDoneMessage() {
        System.out.println("Nice! I've marked this task as done:");
    }

    public void noTaskMessage() {
        System.out.println("No tasks in list");
    }

    public void taskFoundMessage() {
        System.out.println("Here are the matching tasks in your list:");
    }

    public void taskNotFoundMessage() {
        System.out.println("No tasks match the given query");
    }
}
