package Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextParser {
    // Load Map
    public static Map<String, List<String>> loadMap(String filename) {
        Map<String, List<String>> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by colon to separate key and values
                String[] keyValue = line.split(":");

                if (keyValue.length == 2) {
                    String key = keyValue[0].trim(); // Get the key
                    // Get the values as a list, removing whitespace and splitting by comma
                    List<String> values = Arrays.asList(keyValue[1].trim().split(",\\s*"));

                    data.put(key, values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Load Valid Words
    public static Set<String> loadSet(String filename) {
        Set<String> data = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static boolean isValidWord(String word, Set<String> validWords) {
        return validWords.contains(word);
    }
}
