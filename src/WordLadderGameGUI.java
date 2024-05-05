import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Time;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

import javafx.util.Pair;
import Algorithm.Algorithm;
import Mapper.TextParser;
import java.util.ArrayList;

public class WordLadderGameGUI {
    private static Set<String> words;
    private static Map<String, List<String>> data;
    private static String[] colorPalette = { "#00C9A7", "#00AFA6", "#00949C", "#017A8B", "#266074", "#FCFCFC" };

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        int countRow = 0;
        words = new HashSet<>(TextParser.loadSet("dictionary/words.txt"));
        data = TextParser.loadMap("dictionary/map.txt");

        JFrame frame = new JFrame("WordLadderGame");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        // Define fonts
        Font authorFont = new Font("Montserrat", Font.BOLD, 24); // Author line font
        Font inputFont = new Font("Montserrat", Font.BOLD, 20); // Input fields and labels font
        Font textInputFont = new Font("Montserrat", Font.PLAIN, 20); // Text input font

        // Main panel with GridBagLayout for custom arrangement
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode(colorPalette[1])); // Set consistent background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.CENTER; // Center components horizontally

        // Add an image above the title
        gbc.gridx = 0; // First column
        gbc.gridy = countRow; // First row
        gbc.gridwidth = 3; // Span three columns

        ImageIcon imageIcon = new ImageIcon("img/logo.png"); // Modify with the path to your image file

        // Optionally, you can resize the image
        Image scaledImage = imageIcon.getImage().getScaledInstance(275, 184, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        // Add the image to a JLabel
        JLabel imageLabel = new JLabel(imageIcon);
        mainPanel.add(imageLabel, gbc);

        countRow++;
        // Title label spanning three columns
        gbc.gridx = 0; // Start at the first column
        gbc.gridy = countRow; // First row
        gbc.gridwidth = 3; // Span across three columns
        gbc.gridwidth = 1;
        countRow++; // Increment row count

        // Author label spanning three columns
        gbc.gridx = 0; // First column
        gbc.gridy = countRow; // Second row
        gbc.gridwidth = 3;
        JLabel authorLabel = new JLabel("Made by: Imanuel Sebastian Girsang - 13522058", JLabel.CENTER);
        authorLabel.setFont(authorFont); // Set font for the author line
        authorLabel.setForeground(Color.decode(colorPalette[5])); // Set author line color
        authorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(authorLabel, gbc);

        countRow++; // Increment row count
        gbc.gridwidth = 1;

        // First word input field with label
        gbc.gridx = 0; // First column
        gbc.gridy = countRow; // Second row
        JLabel startWordLabel = new JLabel("Start Word:");
        startWordLabel.setFont(inputFont); // Set font for the label
        startWordLabel.setForeground(Color.decode(colorPalette[5])); // Set label color
        mainPanel.add(startWordLabel, gbc);

        gbc.gridx = 1; // Move to the second column
        gbc.gridwidth = 2; // Span two columns
        JTextField startWordField = new JTextField(27); // Width of the text field
        startWordField.setFont(textInputFont); // Set font for the input field
        mainPanel.add(startWordField, gbc);

        countRow++; // Increment row count

        // Second word input field with label
        gbc.gridx = 0; // First column
        gbc.gridy = countRow; // Third row
        JLabel endWordLabel = new JLabel("End Word:");
        endWordLabel.setFont(inputFont); // Set font for the label
        endWordLabel.setForeground(Color.decode(colorPalette[5])); // Set label color
        mainPanel.add(endWordLabel, gbc);

        gbc.gridx = 1; // Move to the second column
        gbc.gridwidth = 2; // Span two columns
        JTextField endWordField = new JTextField(27); // Width of the text field
        endWordField.setFont(textInputFont); // Set font for the input field
        mainPanel.add(endWordField, gbc);

        countRow++; // Increment row count

        // Solver method dropdown with label
        gbc.gridx = 0; // First column
        gbc.gridy = countRow; // Fourth row
        JLabel solverMethodLabel = new JLabel("Algorithm:");
        solverMethodLabel.setFont(inputFont); // Set font for the label
        solverMethodLabel.setForeground(Color.decode(colorPalette[5])); // Set label color
        mainPanel.add(solverMethodLabel, gbc);

        gbc.gridx = 1; // Move to the second column
        gbc.gridwidth = 2; // Span two columns
        JComboBox<String> solverMethodComboBox = new JComboBox<>(new String[] { "UCS", "GBFS", "A*" });
        solverMethodComboBox.setPreferredSize(new Dimension(570, 30)); // Set preferred size
        solverMethodComboBox.setFont(inputFont); // Set font for the combo box
        mainPanel.add(solverMethodComboBox, gbc);

        countRow++; // Increment row count

        // Submit button spanning three columns and centered
        gbc.gridx = 0; // First column
        gbc.gridy = countRow; // Fifth row
        gbc.gridwidth = 3; // Span three columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(inputFont); // Set font for the submit button
        mainPanel.add(submitButton, gbc);

        // Add arrow key navigation and enter key submission
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_ENTER) {
                    // Trigger submit button when Enter is pressed
                    submitButton.doClick();
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    // Move focus to the next field when DOWN is pressed
                    if (e.getSource() == startWordField) {
                        endWordField.requestFocus();
                    }
                } else if (keyCode == KeyEvent.VK_UP) {
                    // Move focus to the previous field when UP is pressed
                    if (e.getSource() == endWordField) {
                        startWordField.requestFocus();
                    }
                }
            }
        };

        startWordField.addKeyListener(keyListener); // Attach to text fields
        endWordField.addKeyListener(keyListener);

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startWord = startWordField.getText().trim().toLowerCase();
                String endWord = endWordField.getText().trim().toLowerCase();
                String solverMethod = (String) solverMethodComboBox.getSelectedItem();

                // Validate inputs

                if (startWord.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Error: Please enter your start word.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (endWord.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Error: Please enter your end word.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!TextParser.isValidWord(startWord, words)) {
                    JOptionPane.showMessageDialog(frame, "Error: The start word is not valid.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!TextParser.isValidWord(endWord, words)) {
                    JOptionPane.showMessageDialog(frame, "Error: The end word is not valid.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (startWord.length() != endWord.length()) {
                    JOptionPane.showMessageDialog(frame,
                            "Error: The start word and end word must have the same length.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If valid, proceed with processing the inputs and showing results
                Algorithm algorithm = new Algorithm(solverMethod, startWord.toLowerCase(), endWord.toLowerCase());

                // Start memory and time counter

                Runtime runtime = Runtime.getRuntime();

                runtime.gc(); // Garbage collection to ensure clean memory

                long memorySebelum = runtime.totalMemory() - runtime.freeMemory();

                Time startTime = new Time(System.currentTimeMillis());
                Pair<ArrayList<String>, Integer> result = algorithm.Evaluate(data);

                long memorySesudah = runtime.totalMemory() - runtime.freeMemory();

                // Calculate memory used

                long memoryUsed = memorySesudah - memorySebelum;
                Time endTime = new Time(System.currentTimeMillis());

                long timeTaken = endTime.getTime() - startTime.getTime();

                // Show the results in a new window
                showResults(startWord, endWord, timeTaken, result.getValue(), result.getKey().size(),
                        result.getKey(), memoryUsed);
            }
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void showResults(String startWord, String endWord, long timeTaken, int visitedNodes, int pathLength,
            ArrayList<String> path, long memoryUsed) {
        JFrame resultFrame = new JFrame("WordLadder Results");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(900, 900);

        // Center the frame horizontally on the screen
        resultFrame.setLocationRelativeTo(null);

        // Set up fonts
        Font summaryFont = new Font("Montserrat", Font.BOLD, 20);

        Font resultFont = new Font("Montserrat", Font.BOLD, 40);
        Font pathFont = new Font("Montserrat", Font.BOLD, 28);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a summary panel with GridBagLayout
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding
        gbc.anchor = GridBagConstraints.CENTER;
        // Set consistent background color

        int currentRow = 0;

        // Add the result label
        gbc.gridx = 0; // Leftmost position
        gbc.gridy = currentRow; // First row
        gbc.gridwidth = 1; // Reset grid width
        JLabel resultLabel = new JLabel("Result of", SwingConstants.CENTER);
        resultLabel.setFont(resultFont);
        resultLabel.setForeground(Color.decode(colorPalette[5])); // Set label color
        resultLabel.setBackground(Color.decode(colorPalette[5]));
        summaryPanel.add(resultLabel, gbc);

        currentRow++; // Increment row count

        gbc.gridy = currentRow; // Second row

        String text = String.format("<b>%s</b> to <b>%s</b>", startWord.toUpperCase(), endWord.toUpperCase());

        JLabel pathLabel = new JLabel("<html>" + text + "</html>", SwingConstants.CENTER); // Use HTML formatting

        pathLabel.setFont(resultFont);
        pathLabel.setForeground(Color.decode(colorPalette[5])); // Set label color
        pathLabel.setBackground(Color.decode(colorPalette[5]));
        summaryPanel.add(pathLabel, gbc);

        // Add time taken
        currentRow++;
        gbc.gridy = currentRow;
        summaryPanel.add(createStyledLabel("Time Taken: " + timeTaken + " ms", summaryFont), gbc);

        currentRow++;
        gbc.gridy = currentRow;
        summaryPanel.add(createStyledLabel("Memory used: " + memoryUsed + " bytes", summaryFont), gbc);

        // Add visited nodes
        currentRow++;
        gbc.gridy = currentRow;
        summaryPanel.add(createStyledLabel("Visited Nodes: " + visitedNodes, summaryFont), gbc);

        // Add path length
        currentRow++;
        gbc.gridy = currentRow;
        summaryPanel.add(createStyledLabel("Path Length: " + pathLength, summaryFont), gbc);

        currentRow++;
        gbc.gridy = currentRow;

        Icon imageIcon;
        if (path != null && !path.isEmpty()) {
            imageIcon = new ImageIcon("img/succsess.gif");
        } else {
            imageIcon = new ImageIcon("img/crybaby.gif");
        }

        // Add the image to a JLabel
        JLabel imageLabel = new JLabel(imageIcon);
        summaryPanel.add(imageLabel, gbc);
        summaryPanel.setBackground(Color.decode(colorPalette[1])); // Set consistent background color
        mainPanel.add(summaryPanel, BorderLayout.NORTH);

        // Paths Panel
        JPanel pathsPanel = new JPanel(new GridBagLayout()); // Layout depends on whether there's a solution
        GridBagConstraints pathGbc = new GridBagConstraints();
        pathGbc.gridx = 0; // Posisi kolom pasti sama,
        pathGbc.gridy = 0; // Posisi row dimulai dari 0
        pathGbc.insets = new Insets(5, 5, 5, 5);
        pathsPanel.setBackground(Color.decode(colorPalette[1])); // Set consistent background color

        if (path != null && !path.isEmpty()) {

            for (int i = 0; i < path.size(); i++) {
                String word = path.get(i);
                JPanel wordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                for (int j = 0; j < word.length(); j++) {
                    JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    boxPanel.setPreferredSize(new Dimension(50, 50)); // Uniform size for the box
                    boxPanel.setBackground(Color.BLACK); // Default background color
                    boxPanel.setOpaque(true); // Ensure opacity

                    JLabel letterLabel = new JLabel(String.valueOf(word.charAt(j)), JLabel.CENTER);
                    letterLabel.setFont(pathFont); // Set font for the letter
                    letterLabel.setForeground(Color.WHITE); // Default text color

                    if (word.charAt(j) == endWord.charAt(j)) { // Matching letter
                        boxPanel.setBackground(Color.decode(colorPalette[0])); // Green for matched letters
                        letterLabel.setForeground(Color.BLACK); // Change text color to black
                    }

                    if (i > 0 && word.charAt(j) != path.get(i - 1).charAt(j)) {
                        // Apply a border to emphasize the box around the letter
                        boxPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5)); // Box border
                    }

                    boxPanel.add(letterLabel); // Add the letter to the box panel
                    wordPanel.add(boxPanel); // Add box panel to word panel
                }
                pathGbc.gridy = i; // Set the row for the current word

                pathsPanel.add(wordPanel, pathGbc); // Add word panel to paths panel
            }
        } else {
            // If no solution, display a message
            pathsPanel.setLayout(new GridLayout(1, 1));
            JLabel noSolutionLabel = createStyledLabel("No solution found :((", summaryFont);
            pathsPanel.add(noSolutionLabel);
        }

        JScrollPane scrollPane = new JScrollPane(pathsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // Set consistent scroll pane background

        mainPanel.add(scrollPane); // Add scroll pane to the main panel

        resultFrame.add(mainPanel);
        resultFrame.setVisible(true); // Show the results frame
    }

    // Helper method to create styled labels with specific font
    private static JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.decode(colorPalette[5])); // Set label color

        return label;
    }

}
