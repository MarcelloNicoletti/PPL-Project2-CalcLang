import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Executor {
    private Map<String, BigDecimal> memory;

    public Executor (Set<String> identifierSet) {
        this.memory = new HashMap<>();
        for (String identifier : identifierSet) {
            memory.put(identifier, BigDecimal.ZERO);
        }
    }

    public void execute (Node node) {
        switch (node.getNodeType()) {
            case PROGRAM:
                break;
            case STATEMENTS:
                break;
            case STATEMENT:
                break;
            case MESSAGE:
                break;
            case INPUT:
                break;
            case ASSIGNMENT:
                break;
        }
    }

    public BigDecimal evaluate (Node node) {
        switch (node.getNodeType()) {
            case VARIABLE:
                return memory.get(node.getData());
            case EXPRESSION:
                break;
            case TERM:
                break;
            case FACTOR:
                break;
            case NUMBER:
                return new BigDecimal(node.getData());
        }
        return null;
    }
}
