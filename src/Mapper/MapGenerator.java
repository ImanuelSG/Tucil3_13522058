package Mapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MapGenerator {

    // Generate all possible one-letter variants of a word that are valid words
    // (Basically nyoba semua kombinasi satu huruf yang mungkin dari kata yang diberikan)
    public static List<String> generateOneLetterVariants(String word, Set<String> validWords) {
        List<String> variants = new ArrayList<>();
        char[] wordChars = word.toCharArray();

        for (int i = 0; i < wordChars.length; i++) {
            char originalChar = wordChars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    wordChars[i] = c;
                    String newWord = new String(wordChars);

                    // kalau kata yang baru valid
                    if (validWords.contains(newWord)) {
                        variants.add(newWord);
                    }
                }
            }
            wordChars[i] = originalChar;
        }

        Collections.sort(variants); // Sort variants in lexicographical order
        return variants;
    }

    public static void main(String[] args) {
        // Open the file containing the words
        File inputFile = new File("dictionary/words.txt");
        Set<String> validWords = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    validWords.add(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Create the key-value mapping
        Map<String, List<String>> wordMap = new HashMap<>();
        for (String word : validWords) {
            List<String> variants = generateOneLetterVariants(word, validWords);
            if (!variants.isEmpty()) {
                wordMap.put(word, variants);
            }
        }

        // Sort the keys of the map
        List<String> sortedKeys = new ArrayList<>(wordMap.keySet());
        Collections.sort(sortedKeys);

        // Save the key-value pairs to a text file
        File outputFile = new File("dictionary/map.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String key : sortedKeys) {
                List<String> variants = wordMap.get(key);
                String formattedVariants = variants.stream().collect(Collectors.joining(", "));
                String line = String.format("%s: %s\n", key, formattedVariants);

                writer.write(line);
            }

            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }

        System.out.println("Key-value mapping saved to 'map.txt'");
    }
}
