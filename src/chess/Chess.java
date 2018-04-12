// Chess.java
// Sassan Hashemi

package chess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;



public class Chess {

    public static void main(String[] args) {
        boolean choice = true;
        while (choice) {
            choice = Chess.introMessage();
            Chess.play(choice);
        }
    }

    private static Boolean introMessage() {
        System.out.println("Welcome to Chess! Please make a selection");
        System.out.println("\"play\": Play chess");
        System.out.println("\"quit\": Exit the game");

        String choice = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        while (!(choice.equals("play") || choice.equals("quit"))) {
            System.out.println("Invalid choice, try again.");
            try {
                choice = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        return (choice.equals("play"));

    }

    private static void play(boolean play) {
        if (play) {
            Game game = new Game();
        } else {
            System.out.println("Have a nice day!");
        }
    }

}
