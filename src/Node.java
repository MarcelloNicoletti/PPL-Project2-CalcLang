import java.util.LinkedList;
import java.util.List;

public class Node {
    private final NodeType nodeType;
    private String data;
    private final Node parent;
    private final List<Node> children;

    public Node () {
        this.nodeType = NodeType.PROGRAM;
        this.data = null;
        this.parent = null;
        this.children = new LinkedList<>();
    }

    public Node (NodeType nodeType, String data, Node parent) {
        this.nodeType = nodeType;
        this.data = data;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public NodeType getNodeType () {
        return nodeType;
    }

    public String getData () {
        return data;
    }

    public void setData (String data) {
        this.data = data;
    }

    public Node getParent () {
        return parent;
    }

    public List<Node> getChildren () {
        return new LinkedList<>(children);
    }

    public Node getChild (int n) {
        if (n >= 0 && n < children.size()) {
            return children.get(n);
        } else {
            return null;
        }
    }

    public void addChildNode (Node node) {
        children.add(node);
    }

    // TODO: Execution here?
}
