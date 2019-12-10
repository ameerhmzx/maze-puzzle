package solutionStrategies;

import enums.Direction;
import objects.Board;
import objects.Cell;
import objects.Graph;

import java.util.ArrayList;

public abstract class GraphSolutionStrategy {
    Graph mazeToGraph(Board board, Cell currentCell, Cell finishCell) {
        Graph graph = new Graph();

        for (Cell cell : board.getCells()) {
            ArrayList<Cell> neighbourCells = board.getVisitableNeighbourCells(cell);

            if (neighbourCells.size() != 2 || cell.equals(currentCell) || cell.equals(finishCell) || !isPassage(cell)) {
                for (Cell neighbourCell : neighbourCells) {
                    graph.addNode(cell.getIndex(), cell.getLocation(), cell.getInterWall(neighbourCell));
                }
            }
        }

        return graph;
    }

    boolean isPassage(Cell cell) {
        boolean hasUpCell = !cell.hasWall(Direction.UP);
        boolean hasDownCell = !cell.hasWall(Direction.DOWN);
        boolean hasLeftCell = !cell.hasWall(Direction.LEFT);
        boolean hasRightCell = !cell.hasWall(Direction.RIGHT);
        return (hasUpCell && hasDownCell) || (hasLeftCell && hasRightCell);
    }
}
