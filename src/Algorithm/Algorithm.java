package Algorithm;

import DataStructure.Node;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class Algorithm {

    private String methodType;
    private String startWord;
    private String endWord;
    private Map<String, String> visitedMap;

    public Algorithm(String methodType, String startWord, String endWord) {
        this.methodType = methodType;
        this.startWord = startWord;
        this.endWord = endWord;
        this.visitedMap = new HashMap<>();
    }

    public ArrayList<String> getPaths() {
        ArrayList<String> path = new ArrayList<>();
        String current = endWord;
        while (current != "") {
            path.add(current);
            current = visitedMap.get(current);
        }
        return Algorithm.reverseList(path);
    }

    public static ArrayList<String> reverseList(ArrayList<String> list) {
        ArrayList<String> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        return reversedList;
    }

    // Method to evaluate heuristic using Hamming distance (counting character
    // differences)
    public int evaluateHeuristic(String thisValue, String goalValue) {
        int counter = 0;
        // Ensure the lengths are the same
        if (thisValue.length() != goalValue.length()) {
            throw new IllegalArgumentException("The words must be of the same length.");
        }
        // Count character differences
        for (int i = 0; i < thisValue.length(); i++) {
            if (thisValue.charAt(i) != goalValue.charAt(i)) {
                counter++; // Increment counter when characters differ
            }
        }
        return counter;
    }

    // Method to evaluate the A* cost: g(n) + h(n)
    public int evaluateAStar(String currentWord, int level, String goalWord) {

        return level + evaluateHeuristic(currentWord, goalWord); // A* evaluation
    }

    // Decide the used cost based on the method
    public int evaluatePrice(int level, String currentWord) {
        if (this.methodType.equals("UCS")) {
            return level;
        } else if (this.methodType.equals("GBFS")) {
            return evaluateHeuristic(currentWord, this.endWord);
        } else if (this.methodType.equals("A*")) {
            return evaluateAStar(currentWord, level, this.endWord);
        } else {
            throw new IllegalArgumentException("Invalid method type.");
        }
    }

    public Pair<ArrayList<String>, Integer> Evaluate(Map<String, List<String>> maps) {

        PriorityQueue<Node> queue = new PriorityQueue<>(Node.compareByCost());
        Node root = new Node(null, startWord, evaluatePrice(0, startWord), 0);

        Map<String, Integer> visitedDouble = new HashMap<>();

        int counter = 0;
        int doubleCounter = 0;

        queue.add(root);
        while (!queue.isEmpty()) {
            // get the current node
            Node current = queue.poll();

            // increment the counter
            counter++;
            // prevent revisiting the same node
            if (visitedMap.get(current.getValue()) != null) {
                continue;
            }
            // prevent null accsess on parent
            visitedMap.put(current.getValue(),
                    (current.getParent() != null) ? current.getParent().getValue() : "");
            // if already goal, return the path
            if (current.getValue().equals(endWord)) {
                System.out.println(doubleCounter);
                return new Pair<>(getPaths(), counter);
            }
            // get the neighbors of the current node (basically expanding the node)
            List<String> neighbors = maps.get(current.getValue());
            // iterate ke semua tetangga, kalau misalkan ternyata tetangga udah abis, maka
            // ganti node.
            if (neighbors == null) {
                continue;
            }
            for (String neighbor : neighbors) {
                // Only add to the queue if the node hasnt been visited (Hanya masukkan ke
                // simpul hidup bila belom pernah ekspan)
                if (visitedMap.get(neighbor) == null) {

                    // Basically we prune the same node that are already too expensive
                    if (visitedDouble.get(neighbor) != null
                            && visitedDouble.get(neighbor) <= evaluatePrice(current.getDepth() + 1, neighbor)) {
                        continue;
                    }

                    
                    Node newNode = new Node(current, neighbor, evaluatePrice(current.getDepth() + 1, neighbor),
                            current.getDepth() + 1);
                    queue.add(newNode);
                    // visitedDouble untuk keep track node yang udah pernah masuk ke queue dengan
                    // cost yang ada
                    // (ini digunakan untuk memprune potensial node yang lebih mahal)
                    visitedDouble.put(neighbor, evaluatePrice(current.getDepth() + 1, neighbor));
                }
            }

        }
        System.out.println("No path found");
        return new Pair<>(new ArrayList<>(), counter);
    }

}
