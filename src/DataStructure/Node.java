package DataStructure;

import java.util.Comparator;

public class Node {
    private Node parent; // Parent node dari simpul sekarang, untuk root parent = null
    private String value; // Kata yang disimpan di simpul
    private int cost; // Harga untuk mencapai simpul
    private int depth; // Kedalaman simpul

    // Constructor initializes parent and value, for root it will be null

    public Node(Node parent, String value, int cost, int depth) {
        this.parent = parent;
        this.value = value;
        this.cost = cost;
        this.depth = depth;
    }

    // Getter for parent
    public Node getParent() {
        return this.parent;
    }

    // Getter for value
    public String getValue() {
        return this.value;
    }

    // Getter for cost
    public int getCost() {
        return this.cost;
    }

    // Getter for depth
    public int getDepth() {
        return this.depth;
    }

    // Method to compare two nodes by their cost
    public static Comparator<Node> compareByCost() {
        return (node1, node2) -> Integer.compare(node1.getCost(), node2.getCost());
    }

}
