package layoutStrategies;

import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Prim's Randomization Algorithm
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm
 *
 * Generates relatively easy to solve mazes with short dead ends
 */
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
