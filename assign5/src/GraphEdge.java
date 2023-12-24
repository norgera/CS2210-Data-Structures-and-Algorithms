public class GraphEdge {

    // Instance variables
    private GraphNode u;
    private GraphNode v;
    private int type;
    private String label;

    // Constructor for graph edges with specified endpoints, type, and label
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    // Returns the first endpoint of the edge
    public GraphNode firstEndpoint() {
        return this.u;
    }

    // Returns the second endpoint of the edge
    public GraphNode secondEndpoint() {
        return this.v;
    }

    // Returns the type of the edge
    public int getType() {
        return this.type;
    }

    // Sets a new type for the edge
    public void setType(int newType) {
        this.type = newType;
    }

    // Returns the label of the edge
    public String getLabel() {
        return this.label;
    }

    // Sets a new label for the edge
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    public boolean contains(GraphNode v2) {
        return false;
    }
}
