package Stress;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.util.Pair;

import Algorithm.Algorithm;
import Mapper.TextParser;

public class Stress {
    private static final String RED = "\u001B[31m"; // ANSI code for red
    private static final String RESET = "\u001B[0m"; // Reset color

    private static List<String> readWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }
        return words;
    }

    public static void main(String[] args) {
        try {
            List<String> words = readWordsFromFile("words_alpha.txt");
            Map<String, List<String>> data = TextParser.LoadMap("output.txt");

            // Create a thread pool to handle multiple tasks concurrently
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            // List to store tasks
            List<Runnable> tasks = new ArrayList<>();

            // Iterate over all pairs of words
            for (int i = 0; i < words.size(); i++) {
                for (int j = i + 1; j < words.size(); j++) {
                    if (words.get(i).length() != words.get(j).length()) {
                        continue; // Skip words of different lengths
                    }

                    final String word1 = words.get(i);
                    final String word2 = words.get(j);

                    tasks.add(() -> {
                        Algorithm ucsAlgorithm = new Algorithm("UCS", word1, word2);
                        Algorithm aStarAlgorithm = new Algorithm("A*", word1, word2);

                        Pair<ArrayList<String>, Integer> ucsResult = ucsAlgorithm.Evaluate(data);
                        Pair<ArrayList<String>, Integer> aStarResult = aStarAlgorithm.Evaluate(data);

                        int ucsLength = ucsResult.getKey().size(); // Length of UCS path
                        int aStarLength = aStarResult.getKey().size(); // Length of A* path

                        if (ucsLength != aStarLength) {
                            System.out.println(RED +
                                    "Mismatch detected for words '" + word1 + "' and '" + word2 + "': " +
                                    "UCS = " + ucsLength + ", A* = " + aStarLength + RESET);
                            System.exit(1); // Exit if there's a mismatch
                        }
                    });
                }
            }

            // Submit tasks to the thread pool
            tasks.forEach(executorService::submit);

            // Shutdown the executor and wait for completion
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);

            System.out.println("Comparison completed without discrepancies.");

        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error waiting for threads to complete: " + e.getMessage());
        }
    }
}
