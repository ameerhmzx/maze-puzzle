package layoutStrategies;

import objects.Board;
import objects.Cell;

import java.util.ArrayList;

/**
 * Depth first recursive backtrack maze generation algorithm
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
 */
public class RecursiveBackTrackLayoutStrategy implements ILayoutStrategy {
    @Override
    public void layoutBoard(Board board) {
        ArrayList<Integer> visitedCellIndices = new ArrayList<>();

        this.visitCell(board.getRandomCell(), visitedCellIndices, board);
    }

    private void visitCell(Cell cell, ArrayList<Integer> visitedCellIndices, Board board) {
        visitedCellIndices.add(cell.getIndex());
        ArrayList<Cell> cells = board.getNeighbourCells(cell);

        while (cells.size() != 0) {
            Cell randomCell = cells.get((int) (Math.random() * cells.size()));
            if (!visitedCellIndices.contains(randomCell.getIndex())) {
                cell.removeInterWall(randomCell);
                this.visitCell(randomCell, visitedCellIndices, board);
            }
            cells.remove(randomCell);
        }
    }
}