package objects;

import java.util.ArrayList;

public class Path<T> implements Comparable<Path> {
    private ArrayList<T> nodes;
    private int distance;

    public Path(T node) {
        this(node, 0);
    }

    public Path(T node, int distance) {
        this.nodes = new ArrayList<>();
        this.nodes.add(node);
        this.distance = 0;
    }

    public Path(Path<T> path) {
        this.nodes = new ArrayList<>(path.nodes);
        this.distance = path.distance;
    }

    public ArrayList<T> getNodes() {
        return nodes;
    }

    public int getDistance() {
        return distance;
    }

    public T getHead() {
        return this.nodes.get(this.nodes.size() - 1);
    }

    public T getTail() {
        return this.nodes.get(0);
    }

    public void addNode(T node) {
        this.addNode(node, 0);
    }

    public boolean contains(T node) {
        return this.nodes.contains(node);
    }

    public void addNode(T node, int distance) {
        this.nodes.add(node);
        this.distance += distance;
    }

    @Override
    public int compareTo(Path path) {
        if (this.distance > path.getDistance())
            return 1;
        if (this.distance < path.getDistance())
            return -1;
        if (this.distance == path.getDistance()) {
            if (this.nodes.size() > path.getNodes().size())
                return 1;
            if (this.nodes.size() < path.getNodes().size())
                return -1;
        }
        return 0;
    }
}