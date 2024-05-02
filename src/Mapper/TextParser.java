package Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextParser {
    public static Map<String, List<String>> LoadMap(String filename) {
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
}
