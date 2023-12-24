import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {
    private GraphNode[] nodes;
    private Map<GraphNode, List<GraphEdge>> adjacencyList;

    // Constructor initializes nodes and adjacency list
    public Graph(int n) {
        nodes = new GraphNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new GraphNode(i);
        }

        adjacencyList = new HashMap<>();
        for (GraphNode node : nodes) {
            adjacencyList.put(node, new ArrayList<>());
        }
    }

    // Inserts an edge between two nodes
    public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {
        validateNode(u);
        validateNode(v);

        GraphEdge newEdge = new GraphEdge(u, v, edgeType, label);
        adjacencyList.get(u).add(newEdge);
        if (!u.equals(v)) {
            adjacencyList.get(v).add(newEdge);
        }
    }

    // Returns specific node
    public GraphNode getNode(int name) throws GraphException {
        validateNodeIndex(name);
        return nodes[name];
    }

    // Returns iterator over incident edges of node
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        validateNode(u);
        List<GraphEdge> incidentEdges = adjacencyList.get(u);
        return incidentEdges.iterator();
    }

    // Return edge between two nodes
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        validateNode(u);
        validateNode(v);

        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.contains(v)) {
                return edge;
            }
        }
        throw new GraphException("Edge doesn't exist");
    }

    // Checks if nodes are adjacent
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        validateNode(u);
        validateNode(v);

        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.contains(v)) {
                return true;
            }
        }
        return false;
    }

    // Define the contains method in the GraphEdge class
    public boolean contains(GraphNode node) {
        return node.equals(node) || node.equals(this);
    }

    private void validateNode(GraphNode node) throws GraphException {
        if (node == null || node.getName() < 0 || node.getName() >= nodes.length) {
            throw new GraphException("Node doesn't exist");
        }
    }

    private void validateNodeIndex(int index) throws GraphException {
        if (index < 0 || index >= nodes.length) {
            throw new GraphException("Node index is out of bounds");
        }
    }
}
