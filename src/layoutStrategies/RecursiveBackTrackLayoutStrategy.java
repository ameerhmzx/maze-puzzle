package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

/**
 * Depth first recursive backtrack maze generation algorithm
 * https://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 * <p>
 * Generates long dead ends making the solution little difficult
 */
public class RecursiveBackTrackLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<Cell> visitedCells = new ArrayList<>();
        Cell randomCell = board.getRandomCell();

        layoutChanges.add(LayoutChange.SET_CURRENT_CELL, randomCell);
        this.visitCell(randomCell, visitedCells, board, layoutChanges);
        return layoutChanges;
    }

    private void visitCell(Cell cell, ArrayList<Cell> visitedCells, Board board, LayoutChanges layoutChanges) {
        visitedCells.add(cell);
        ArrayList<Cell> cells = board.getNeighbourCells(cell);

        while (cells.size() != 0) {
            Cell randomCell = cells.get((int) (Math.random() * cells.size()));
            if (!visitedCells.contains(randomCell)) {
                cell.removeInterWall(randomCell);
                layoutChanges.add(LayoutChange.MOVE, cell, cell.getInterWall(randomCell));
                layoutChanges.add(LayoutChange.TOUCH_CELL, randomCell);
                this.visitCell(randomCell, visitedCells, board, layoutChanges);
            }
            cells.remove(randomCell);
            layoutChanges.add(LayoutChange.UNTOUCH_CELL, randomCell);
        }
    }
}