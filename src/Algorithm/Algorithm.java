package Algorithm;

import DataStructure.Node;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class Algorithm {

    private String methodType;// Untuk nyimpen metode yang dipake
    private String startWord;// Untuk nyimpen kata awal
    private String endWord;// Untuk nyimpen kata akhir
    private Map<String, String> visitedMap;// Biar tidak terjadi cycle, disimpan semua visitedNode dan siapa parentnya,
                                           // Parent dari kata pertama adalah "".

    // Constructor, yang membedakan UCS,GBF,dan A* adalah cara menghitung cost
    public Algorithm(String methodType, String startWord, String endWord) {
        this.methodType = methodType;
        this.startWord = startWord;
        this.endWord = endWord;
        this.visitedMap = new HashMap<>();
    }

    // Untuk mendapatkan path dari startWord ke endWord (dengan visitedMap)
    // basically reverse engineering :)
    public ArrayList<String> getPaths() {
        ArrayList<String> path = new ArrayList<>();
        String current = endWord;

        // Dicari dari kata terakhir ke kata pertama (ditandai dengan parent = "")
        while (current != "") {
            path.add(current);
            current = visitedMap.get(current);
        }
        return Algorithm.reverseList(path);
    }

    // Method untuk membalikkan list
    public static ArrayList<String> reverseList(ArrayList<String> list) {
        ArrayList<String> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        return reversedList;
    }

    // Method to evaluate heuristic using Hamming distance (counting character
    // differences), semkain kecil semakin baik.
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

    // Method to evaluate the A* cost: g(n) + h(n), level adalah depth dari node
    // yang dievaluasi.
    public int evaluateAStar(String currentWord, int level, String goalWord) {

        return level + evaluateHeuristic(currentWord, goalWord); // A* evaluation
    }

    // Method untuk mengevaluasi cost dari node, tergantung dari metode yang dipakai
    // Kalau UCS akan mengembalik depthnya,
    // Kalau GBFS akan mengembalik heuristicnya,
    // Kalau A* akan mengembalik g(n) + h(n)
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

    // Method untuk mengevaluasi path dari startWord ke endWord
    // Parameter maps merupakan mapping antara kata dengan tetangganya (kata valid
    // yang bisa dicapai dengan menggani salah satu huruf)
    public Pair<ArrayList<String>, Integer> Evaluate(Map<String, List<String>> maps) {

        // Priority queue untuk menyimpan node yang akan diekspan, compareByCost
        // membandingkan cost dari node
        PriorityQueue<Node> queue = new PriorityQueue<>(Node.compareByCost());
        Node root = new Node(null, startWord, evaluatePrice(0, startWord), 0);

        // visitedDouble untuk menyimpan node yang sudah pernah diekspan dan harganya.
        // Ini untuk memprune node yang sudah terlalu mahal untuk tidak dimasukkan ke
        // queue
        Map<String, Integer> visitedDouble = new HashMap<>();

        // Ini untuk tahu berapa note visited
        int counter = 0;

        queue.add(root);

        // Selama queue belum penuh maka lanjut terus
        while (!queue.isEmpty()) {

            Node current = queue.poll();

            counter++;

            // Kalau sudah pernah di ekspan, maka skip
            if (visitedMap.get(current.getValue()) != null) {
                continue;
            }

            // Jika belum, maka masukkan ke visitedMap
            visitedMap.put(current.getValue(),
                    (current.getParent() != null) ? current.getParent().getValue() : "");

            // Jika sudah sampai ke endWord, maka return path dan jumlah node yang
            // dikunjungi
            if (current.getValue().equals(endWord)) {

                return new Pair<>(getPaths(), counter);
            }

            // Ambil semua tetangga dari node yang sedang diekspan
            List<String> neighbors = maps.get(current.getValue());
            // Jika tidak ada tetangga, maka skip
            if (neighbors == null) {
                continue;
            }

            // Iterasi setiap neighbor
            for (String neighbor : neighbors) {

                // Hanya consider yang belum pernah di ekspan
                if (visitedMap.get(neighbor) == null) {

                    // Kalau sudah pernah dimasukkan ke queue namun cost ini lebih mahal, maka
                    // continue (menghindari double expanding)
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

        // Jika tidak ada path yang ditemukan, maka return empty path dan jumlah node
        // yang dikunjungi
        System.out.println("No path found");
        return new Pair<>(new ArrayList<>(), counter);
    }

}
