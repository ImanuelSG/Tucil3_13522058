import java.util.Scanner;
import java.util.Set;

import Algorithm.Algorithm;
import Mapper.TextParser;
import javafx.util.Pair;
import java.util.Map;
import java.util.List;
import java.sql.Time;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class WordLadderGameCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Load the data for the word ladder
            Map<String, List<String>> data = TextParser.loadMap("dictionary/map.txt");
            Set<String> words = TextParser.loadSet("dictionary/words.txt");

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
                        String word1, word2;
                        do {
                            System.out.print("Enter the first word: ");
                            word1 = scanner.nextLine().trim().toLowerCase();

                            if (!TextParser.isValidWord(word1, words)) {
                                System.out.println("Error: The first word is not valid.");
                            }
                        } while (!TextParser.isValidWord(word1, words)); // Continue until the word is valid

                        // Get a valid second word, ensuring it's the same length as the first word
                        do {
                            System.out.print("Enter the second word: ");
                            word2 = scanner.nextLine().trim().toLowerCase();

                            if (!TextParser.isValidWord(word2, words)) {
                                System.out.println();
                                System.out.println("Error: The second word is not valid.");
                                System.out.println();
                            } else if (word1.length() != word2.length()) {
                                System.out.println();
                                System.out.println("Error: The second word must be the same length as the first word.");
                                System.out.println();
                            }
                        } while (!TextParser.isValidWord(word2, words) || word1.length() != word2.length());

                        System.out.println("Select the solver method:");
                        System.out.println("1. UCS");
                        System.out.println("2. GBFS");
                        System.out.println("3. A*");

                        // Validate the solver method choice
                        int solverChoice = 0; // Initialize with an invalid value
                        boolean validInput = false;

                        while (!validInput) {
                            try {
                                System.out.print("Enter a solver method (1-3): "); // Request input
                                solverChoice = scanner.nextInt(); // Read integer input
                                scanner.nextLine(); // Clear any leftover newline

                                if (solverChoice < 1 || solverChoice > 3) { // Validate range
                                    System.out.println("Error: Available methods are 1, 2, or 3. Please try again.");
                                } else {
                                    validInput = true; // Valid input received
                                }
                            } catch (InputMismatchException e) { // Handle non-integer input
                                System.out.println("Error: Please enter a valid number between 1 and 3.");
                                scanner.nextLine(); // Clear invalid input from the buffer
                            }
                        }

                        // Map the choice to the solver method name
                        String solverMethod;
                        switch (solverChoice) {
                            case 1:
                                solverMethod = "UCS";
                                break;
                            case 2:
                                solverMethod = "GBFS";
                                break;
                            case 3:
                                solverMethod = "A*";
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + solverChoice);
                        }

                        // Create the algorithm instance and evaluate

                        Algorithm algorithm = new Algorithm(solverMethod, word1.toLowerCase(), word2.toLowerCase());
                        System.out.println("Solver method: " + solverMethod + "\n" + "Start word: " + word1 + "\n"
                                + "End word: " + word2 + "\n");

                        Runtime runtime = Runtime.getRuntime();

                        // Run the algorithm and measure the time taken
                        runtime.gc();

                        long memorySebelum = runtime.totalMemory() - runtime.freeMemory();

                        Time startTime = new Time(System.currentTimeMillis());
                        Pair<ArrayList<String>, Integer> result = algorithm.Evaluate(data);

                        long memorySesudah = runtime.totalMemory() - runtime.freeMemory();

                        long memory = memorySesudah - memorySebelum;
                        Time endTime = new Time(System.currentTimeMillis());

                        System.out
                                .println("Time taken: " + (endTime.getTime() - startTime.getTime()) + " milliseconds");
                        System.out.println("Memory Usage: " + memory + " bytes");
                        // Display the results
                        if (result.getKey().size() != 0) {
                            System.out.println("Path: " + result.getKey());
                            System.out.println("Visited Nodes: " + result.getValue());
                            System.out.println("Path Length: " + (result.getKey().size()));
                        }

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
