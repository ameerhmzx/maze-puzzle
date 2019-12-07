package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

public class AldousBroderLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<Cell> visitedCells = new ArrayList<>();
        Cell cell = board.getRandomCell();
        visitedCells.add(cell);

        while (visitedCells.size() < board.getSize()) {
            ArrayList<Cell> randomCellNeighbours = board.getNeighbourCells(cell);
            Cell randomCell = randomCellNeighbours.get((int) (randomCellNeighbours.size() * Math.random()));

            if (!visitedCells.contains(randomCell)) {
                randomCell.removeInterWall(cell);
                layoutChanges.add(LayoutChange.MOVE, cell, cell.getInterWall(randomCell));
                visitedCells.add(randomCell);
            }
            cell = randomCell;
        }

        return layoutChanges;
    }
}
