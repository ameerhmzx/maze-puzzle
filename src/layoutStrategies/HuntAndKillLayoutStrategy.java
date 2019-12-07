package layoutStrategies;

import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.Collections;

public class HuntAndKillLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<Cell> visitedCells = new ArrayList<>();

        Cell cell;

        while ((cell = this.getUnvisitedCell(board, visitedCells)) != null) {
            ArrayList<Cell> neighbourCells = board.getNeighbourCells(cell);
            for (Cell neighbourCell : neighbourCells)
                if (visitedCells.contains(neighbourCell)) {
                    cell.removeInterWall(neighbourCell);
                    break;
                }

            randomWalk(cell, visitedCells, board);
        }

        return layoutChanges;
    }

    private Cell getUnvisitedCell(Board board, ArrayList<Cell> visitedCells) {
        Cell unvisitedCell = null;
        for (Cell cell : board.getCells()) {
            if (!visitedCells.contains(cell)) {
                unvisitedCell = cell;
                break;
            }
        }
        return unvisitedCell;
    }

    private void randomWalk(Cell cell, ArrayList<Cell> visitedCells, Board board) {
        visitedCells.add(cell);
        ArrayList<Cell> neighbourCells = board.getNeighbourCells(cell);
        neighbourCells.removeAll(visitedCells);
        Collections.shuffle(neighbourCells);
        if (neighbourCells.size() == 0) return;

        Cell randomCell = neighbourCells.get(0);
        if (!visitedCells.contains(randomCell)) {
            cell.removeInterWall(randomCell);
            visitedCells.add(randomCell);
            randomWalk(randomCell, visitedCells, board);
        }
    }
}
