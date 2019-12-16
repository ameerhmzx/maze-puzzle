package objects;

import enums.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Graph {
    private ArrayList<GraphNode> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(int index, Point location, HashSet<Direction> directions) {
        this.nodes.add(new GraphNode(index, location, directions));
    }

    public void addNode(int index, Point location, Direction direction) {
        GraphNode node = this.getNode(index);
        if (node != null) {
            node.links.add(direction);
        } else {
            addNode(index, location, new HashSet<>() {{
                add(direction);
            }});
        }
    }

    public GraphNode getNode(int index) {
        for (GraphNode node : this.nodes) {
            if (node.index == index) return node;
        }
        return null;
    }

    public GraphNode getNode(int x, int y) {
        for (GraphNode node : this.nodes) {
            if (node.location.y == y && node.location.x == x) return node;
        }
        return null;
    }

    public ArrayList<GraphNode> getAdjacentNodes(GraphNode node) {
        ArrayList<GraphNode> adjacentNodes = new ArrayList<>();
        for (Direction dir : node.links) {
            switch (dir) {
                case UP:
                    for (int y = node.location.y - 1; y >= 0; y--) {
                        GraphNode adjNode = this.getNode(node.location.x, y);
                        if (adjNode != null) {
                            adjacentNodes.add(adjNode);
                            break;
                        }
                    }
                    break;
                case LEFT:
                    for (int x = node.location.x - 1; x >= 0; x--) {
                        GraphNode adjNode = this.getNode(x, node.location.y);
                        if (adjNode != null) {
                            adjacentNodes.add(adjNode);
                            break;
                        }
                    }
                    break;
                case RIGHT:
                    for (int x = node.location.x + 1; ; x++) {
                        GraphNode adjNode = this.getNode(x, node.location.y);
                        if (adjNode != null) {
                            adjacentNodes.add(adjNode);
                            break;
                        }
                    }
                    break;
                case DOWN:
                    for (int y = node.location.y + 1; ; y++) {
                        GraphNode adjNode = this.getNode(node.location.x, y);
                        if (adjNode != null) {
                            adjacentNodes.add(adjNode);
                            break;
                        }
                    }
                    break;
            }
        }

        return adjacentNodes;
    }

    public ArrayList<ArrayList<GraphNode>> getAdjacencyList() {
        ArrayList<ArrayList<GraphNode>> adjacencyList = new ArrayList<>();

        int lastIndex = -1;
        for (GraphNode node : this.nodes) {
            while (++lastIndex != node.index) {
                adjacencyList.add(lastIndex, new ArrayList<>());
            }
            adjacencyList.add(node.index, this.getAdjacentNodes(node));
        }
        return adjacencyList;
    }

    public Integer getNodesDistance(GraphNode node1, GraphNode node2) {
        if (node1.location.y == node2.location.y) {
            int xMin = Math.min(node1.location.x, node2.location.x);
            int xMax = Math.max(node1.location.x, node2.location.x);
            if (xMax - xMin == 1) return 1;
            for (int i = xMin + 1; i < xMax; i++) {
                if (this.getNode(i, node1.location.y) != null) {
                    return -1;
                }
            }
            return xMax - xMin;
        } else if (node1.location.x == node2.location.x) {
            int yMin = Math.min(node1.location.y, node2.location.y);
            int yMax = Math.max(node1.location.y, node2.location.y);
            if (yMax - yMin == 1) return 1;
            for (int i = yMin + 1; i < yMax; i++) {
                if (this.getNode(node1.location.x, i) != null) {
                    return -1;
                }
            }
            return yMax - yMin;
        }

        return -1;
    }

    public void print() {
        ArrayList<ArrayList<GraphNode>> adjacencyList = this.getAdjacencyList();
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.printf("%d -> %s\n", i, Arrays.toString(adjacencyList.get(i).toArray()));
        }
    }
}