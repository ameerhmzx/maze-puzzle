package solutionStrategies;

import enums.Direction;
import objects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DijkstraSolutionStrategy extends GraphSolutionStrategy implements ISolutionStrategy {
    @Override
    public ArrayList<Direction> solve(Cell currentCell, Cell finishCell, Board board) {
        Graph graph = this.mazeToGraph(board, currentCell, finishCell);

        ArrayList<Path<GraphNode>> finishedPaths = new ArrayList<>();
        Stack<Path<GraphNode>> stack = new Stack<>();
        stack.push(new Path<>(graph.getNode(currentCell.getIndex()), 0));

        while (stack.size() > 0) {
            Path<GraphNode> root = stack.pop();
            ArrayList<GraphNode> adjacentNodes = graph.getAdjacentNodes(root.getHead());

            for (GraphNode adjacentNode : adjacentNodes) {
                if (root.contains(adjacentNode)) continue;

                Path<GraphNode> path = new Path<>(root);
                path.addNode(adjacentNode, graph.getNodesDistance(root.getHead(), adjacentNode));


                if (adjacentNode.equals(graph.getNode(finishCell.getIndex()))) {
                    finishedPaths.add(path);
                    finishedPaths.sort(Path::compareTo);
                } else {
                    stack.push(path);
                    stack.sort(Path::compareTo);
                }
            }
        }

        return pathToMoves(finishedPaths.get(0));
    }

    public ArrayList<Direction> pathToMoves(Path<GraphNode> path) {
        ArrayList<Direction> moves = new ArrayList<>();

        GraphNode lastNode = path.getTail();
        for (GraphNode node : path.getNodes()) {
            if (node.equals(lastNode)) continue;

            if (node.getLocation().y == lastNode.getLocation().y) {
                int diff = node.getLocation().x - lastNode.getLocation().x;
                moves.addAll(Collections.nCopies(Math.abs(diff), diff > 0 ? Direction.RIGHT : Direction.LEFT));
            } else if (node.getLocation().x == lastNode.getLocation().x) {
                int diff = node.getLocation().y - lastNode.getLocation().y;
                moves.addAll(Collections.nCopies(Math.abs(diff), diff > 0 ? Direction.DOWN : Direction.UP));
            }

            lastNode = node;
        }
        return moves;
    }
}
