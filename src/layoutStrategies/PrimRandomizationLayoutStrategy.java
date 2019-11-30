package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Prim's Randomization Algorithm
 * http://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm
 * <p>
 * Generates relatively easy to solve mazes with short dead ends
 */
public class PrimRandomizationLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        Cell randomCell = board.getRandomCell();
        ArrayList<Cell> visitedCells = new ArrayList<>();
        Set<Cell> neighbourCells = new HashSet<>(board.getNeighbourCells(randomCell));

        visitedCells.add(randomCell);
        layoutChanges.add(LayoutChange.SET_CURRENT_CELL, randomCell);
        layoutChanges.add(LayoutChange.TOUCH_ALL, neighbourCells);

        while (neighbourCells.size() > 0) {
            randomCell = (Cell) neighbourCells.toArray()[(int) (Math.random() * neighbourCells.size())];
            layoutChanges.add(LayoutChange.SET_CURRENT_CELL, randomCell);
            Set<Cell> randomCellNeighbours = new HashSet<>(board.getNeighbourCells(randomCell));
            layoutChanges.add(LayoutChange.TOUCH_ALL, randomCellNeighbours);

            for (Cell randomCellNeighbour : randomCellNeighbours) {
                if (visitedCells.contains(randomCellNeighbour)) {
                    randomCellNeighbour.removeInterWall(randomCell);
                    layoutChanges.add(LayoutChange.MOVE, randomCell, randomCell.getInterWall(randomCellNeighbour));

                    neighbourCells.remove(randomCell);
                    layoutChanges.add(LayoutChange.UNTOUCH_CELL, randomCell);
                    visitedCells.add(randomCell);
                    neighbourCells.addAll(randomCellNeighbours);
                    neighbourCells.removeAll(visitedCells);
                    break;
                }
            }
        }

        return layoutChanges;
    }
}
