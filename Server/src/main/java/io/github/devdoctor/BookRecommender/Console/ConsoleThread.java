/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Console;

import java.util.Scanner;

public class ConsoleThread extends Thread {
    Scanner scanner;

    public ConsoleThread(Scanner scanner) {
        this.scanner = scanner;
        System.out.println("You are now operative: ");
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("λ BookRecommender > ");
            String command = scanner.nextLine();

            if(command.equals("cake")) {
                System.out.println("The cake is not a lie");
            }
        }
    }
}
