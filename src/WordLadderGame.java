

import java.util.Scanner;
import Algorithm.Algorithm;
import Mapper.TextParser;
import javafx.util.Pair;
import java.util.Map;
import java.util.List;
import java.sql.Time;
import java.util.ArrayList;

public class WordLadderGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Load the data for the word ladder
            Map<String, List<String>> data = TextParser.LoadMap("output.txt");

            // Main menu loop
            boolean running = true;
            while (running) {
                // Display menu options
                System.out.println();
                System.out.println("Choose an option:");
                System.out.println("1. Input words and solver method");
                System.out.println("2. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt(); // Read the user's choice
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        // Get the words and solver method from the user
                        System.out.print("Enter the first word: ");
                        String word1 = scanner.nextLine().trim();

                        System.out.print("Enter the second word: ");
                        String word2 = scanner.nextLine().trim();

                        while (word1.length() != word2.length()) {
                            System.out.println("Error: The two words must have the same length.");
                            System.out.print("Enter the first word: ");
                            word1 = scanner.nextLine().trim();

                            System.out.print("Enter the second word: ");
                            word2 = scanner.nextLine().trim();
                        }

                        System.out.print("Enter the solver method (UCS, GBFS, A*): ");
                        String solverMethod = scanner.nextLine().trim();

                        while (!(solverMethod.equals("UCS") || solverMethod.equals("GBFS")
                                || solverMethod.equals("A*"))) {
                            System.out.println("Error: Available methods are UCS, GBFS, and A*.");
                            System.out.print("Please enter a valid solver method:");
                            solverMethod = scanner.nextLine().trim();
                        }

                        // Create the algorithm instance and evaluate

                        Algorithm algorithm = new Algorithm(solverMethod, word1.toLowerCase(), word2.toLowerCase());
                        System.out.println("Solver method: " + solverMethod + "\n" + "Start word: " + word1 + "\n"
                                + "End word: " + word2 + "\n");

                        Time startTime = new Time(System.currentTimeMillis());
                        Pair<ArrayList<String>, Integer> result = algorithm.Evaluate(data);
                        Time endTime = new Time(System.currentTimeMillis());

                        System.out
                                .println("Time taken: " + (endTime.getTime() - startTime.getTime()) + " milliseconds");

                        // Display the results
                        System.out.println("Path: " + result.getKey());
                        System.out.println("Visited Nodes: " + result.getValue());
                        System.out.println("Path Length: " + (result.getKey().size()));

                        break;

                    case 2:
                        // Exit the program
                        running = false;
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose 1 or 2.");
                        break;
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close(); // Ensure the scanner is closed
        }
    }
}
