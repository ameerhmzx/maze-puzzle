package layoutStrategies;

import interfaces.ILayoutStrategy;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

/**
 * Depth first recursive backtrack maze generation algorithm
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
 */
public class RecursiveBackTrackLayoutStrategy implements ILayoutStrategy {
    private ArrayList<Integer> visitedCellIndices;
    private Board board;

    @Override
    public void layoutBoard(Board board) {
        this.board = board;
        this.visitedCellIndices = new ArrayList<>();

        this.visitCell(board.getRandomCell());
    }

    private void visitCell(Cell cell) {
        this.visitedCellIndices.add(cell.getIndex());
        ArrayList<Cell> cells = board.getNeighbourCells(cell);

        while (cells.size() != 0) {
            Cell randomCell = cells.get((int) (Math.random() * cells.size()));
            if (isCellUnvisited(randomCell)) {
                cell.removeInterWall(randomCell);
                visitCell(cell);
            }
            cells.remove(randomCell);
        }
    }

    private boolean isCellUnvisited(Cell cell) {
        return visitedCellIndices.contains(cell.getIndex());
    }
}