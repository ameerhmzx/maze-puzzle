package layoutStrategies;

import interfaces.ILayoutStrategy;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PrimRandomizationLayoutStrategy implements ILayoutStrategy {
    @Override
    public void layoutBoard(Board board) {
        Cell randomCell = board.getRandomCell();
        ArrayList<Cell> visitedCells = new ArrayList<>();
        Set<Cell> neighbourCells = new HashSet<>(board.getNeighbourCells(randomCell));

        visitedCells.add(randomCell);

        while (neighbourCells.size() > 0) {
            randomCell = (Cell) neighbourCells.toArray()[(int) (Math.random() * neighbourCells.size())];
            Set<Cell> randomCellNeighbours = new HashSet<>(board.getNeighbourCells(randomCell));

            for (Cell randomCellNeighbour : randomCellNeighbours) {
                if (visitedCells.contains(randomCellNeighbour)) {
                    randomCellNeighbour.removeInterWall(randomCell);

                    neighbourCells.remove(randomCell);
                    visitedCells.add(randomCell);
                    neighbourCells.addAll(randomCellNeighbours);
                    neighbourCells.removeAll(visitedCells);
                    break;
                }
            }
        }
    }
}
