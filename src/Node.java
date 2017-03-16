import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private final NodeType nodeType;
    private final String stringData;
    private final BigDecimal numberData;
    private final Node parent;
    private List<Node> children;

    public Node () {
        this.nodeType = NodeType.PROGRAM;
        this.stringData = null;
        this.numberData = null;
        this.parent = null;
        this.children = new LinkedList<>();
    }

    public Node (NodeType nodeType, String data, Node parent) {
        this.nodeType = nodeType;
        this.stringData = data;
        this.numberData = null;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public Node (NodeType nodeType, BigDecimal data, Node parent) {
        this.nodeType = nodeType;
        this.stringData = null;
        this.numberData = data;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public NodeType getNodeType () {
        return nodeType;
    }

    public String getStringData () {
        return stringData;
    }

    public BigDecimal getNumberData () {
        return numberData;
    }

    public Node getParent () {
        return parent;
    }

    public List<Node> getChildren () {
        return children;
    }

    public void addChildNode (Node node) {
        children.add(node);
    }

    // TODO: Execution here?
}
