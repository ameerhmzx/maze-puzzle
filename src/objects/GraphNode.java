package objects;

import enums.Direction;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;

public class GraphNode {
    HashSet<Direction> links;
    Point location;
    Integer index;

    GraphNode(Integer index, Point location, HashSet<Direction> links) {
        this.links = links;
        this.location = location;
        this.index = index;
    }

    public HashSet<Direction> getLinks() {
        return links;
    }

    public Point getLocation() {
        return location;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return index.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.index.equals(((GraphNode) o).index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
