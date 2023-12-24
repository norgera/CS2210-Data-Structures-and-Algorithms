/**
 * Assignment 5 - CS 2210A
 * This program implements graph theory to solve a maze when given an input file.
 * This program uses an adjacency matrix to organize the graph.
 * 
 * @author Nathan Orgera
 * @date December 7, 2023
 */


public class GraphNode {

    // Instance variables
    private int name;
    private boolean marked;

    // Constructor for graph nodes with an integer name
    public GraphNode(int n) {
        this.name = n;
        this.marked = false;
    }

    // Sets the marked status of the node
    public void mark(boolean mark) {
        this.marked = mark;
    }

    // Returns the value of instance variable marked
    public boolean isMarked() {
        return this.marked;
    }

    // Returns the value of instance variable name
    public int getName() {
        return this.name;
    }
}
