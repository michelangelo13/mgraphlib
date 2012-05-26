package core;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for implementing Traversals on the Graph
 *
 * @param <E>
 */
public abstract class Traversal<E> {
    protected Graph<E> g;
    protected Map<Graph.Vertex<E>, Node<E>> v2NodeMap = new HashMap<Graph.Vertex<E>, Node<E>>();

    protected Traversal() {
    }

    public Traversal(Graph<E> graph) {
        this.g = graph;
    }

    abstract protected void traverse();

    protected void traverse(Graph.Vertex<E> startingAt) {

    }

    protected boolean processed(Graph.Vertex<E> v) {
        Node<E> n = v2NodeMap.get(v);
        return n != null && n.isProcessed;
    }

    protected void visitEdge(Graph.Vertex<E> v, Graph.Vertex<E> next) {
        System.out.println(" Edge = From " + v + " to " + next);
    }

    protected void markProcessed(Graph.Vertex<E> v) {
        Node<E> n = v2NodeMap.get(v);
        n.isProcessed = true;

        System.out.println(v + " is processed");
    }

    protected boolean discovered(Graph.Vertex<E> v) {
        Node<E> n = v2NodeMap.get(v);
        return n != null && (n.isDiscovered || n.isProcessed);
    }

    protected void markDiscovered(Graph.Vertex<E> v) {
        // visitVertex(v);
        Node<E> n = v2NodeMap.get(v);
        n = n == null ? new Node<E>(v, true) : n;
        n.isDiscovered = true;
        v2NodeMap.put(v, n);
    }

    protected void visitVertex(Graph.Vertex<E> v) {
        System.out.println("Visiting vertex = " + v);
    }

    protected static class Node<E> {
        Graph.Vertex<E> vertex;
        boolean isDiscovered = false;
        boolean isProcessed = false;
        Graph.Vertex<E> parent;
        Color color = Color.NONE;

        public enum Color {
            RED, BLACK, NONE
        }

        Node(Graph.Vertex<E> vertex, boolean discovered) {
            this.vertex = vertex;
            isDiscovered = discovered;
        }

        boolean setColor(Color color) {
            boolean wasColored = true;
            if (this.color == Color.NONE) {
                this.color = color;
            } else if (this.color != color) {
                System.out.println("Can't color vertex " + vertex + ",initial color=" + this.color
                        + " , attempt to color=" + color);
                wasColored = false;
            }
            return wasColored;
        }

        public Color getColor() {
            return color;
        }

        Color getComplement() {
            return this.color == Color.RED ? Color.BLACK :
                    this.color == Color.NONE ? Color.NONE : Color.RED;
        }

    }
}
