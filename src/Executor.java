import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Executor {
    private Map<String, Double> memory;
    private Scanner input;

    public Executor (Set<String> identifierSet, Scanner input) {
        this.memory = new HashMap<>();
        for (String identifier : identifierSet) {
            memory.put(identifier, 0.0);
        }
        this.input = input;
    }

    public void execute (Node node) {
        if (node == null) {
            return;
        }
        switch (node.getNodeType()) {
            case PROGRAM:
                execute(node.getChild(0));
                break;
            case STATEMENTS:
                execute(node.getChild(0));
                execute(node.getChild(1));
                break;
            case MESSAGE:
                System.out.print(node.getData());
                break;
            case INPUT:
                System.out.print(node.getData());
                double value = input.nextDouble();
                memory.put(node.getChild(0).getData(), value);
                break;
            case ASSIGNMENT:
                memory.put(node.getChild(0).getData(), evaluate(node.getChild(1)));
                break;
            case NEWLINE:
                System.out.println();
                break;
            case SHOW:
                System.out.print(evaluate(node.getChild(0)));
                break;
        }
    }

    public double evaluate (Node node) {
        double left, right;
        switch (node.getNodeType()) {
            case VARIABLE:
                return memory.get(node.getData());
            case NUMBER:
                return Double.parseDouble(node.getData());
            case EXPRESSION:
                left = evaluate(node.getChild(0));
                if (node.getData().isEmpty()) {
                    return left;
                } else if (node.getData().equals("+")) {
                    right = evaluate(node.getChild(1));
                    return left + right;
                } else if (node.getData().equals("-")) {
                    right = evaluate(node.getChild(1));
                    return left - right;
                } else {
                    // TODO: throw Executor error
                    return 0.0;
                }
            case TERM:
                left = evaluate(node.getChild(0));
                if (node.getData().isEmpty()) {
                    return left;
                } else if (node.getData().equals("*")) {
                    right = evaluate(node.getChild(1));
                    return left * right;
                } else if (node.getData().equals("/")) {
                    right = evaluate(node.getChild(1));
                    return left / right;
                } else {
                    // TODO: throw Executor error
                    return 0.0;
                }
            case FACTOR:
                double value = evaluate(node.getChild(0));
                if (node.getData().equals("-")) {
                    value *= -1;
                }
                return value;
            case BIFN:
                double argument = evaluate(node.getChild(0));
                switch (node.getData()) {
                    case "sqrt":
                        return Math.sqrt(argument);
                    case "sin":
                        return Math.sin(argument);
                    case "cos":
                        return Math.cos(argument);
                }
            default:
                // TODO: throw Executor error
                return 0.0;
        }
    }
}
