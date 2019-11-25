package layoutStrategies;

import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Wilson's Maze Generation Algorithm
 * https://weblog.jamisbuck.org/2011/1/20/maze-generation-wilson-s-algorithm
 * <p>
 * It unlike many other algorithms generate generate mazes of unbiased complexity
 */
public class WilsonLayoutStrategy implements ILayoutStrategy {
    public void layoutBoard(Board board) {
        Set<Cell> visitedCells = new HashSet<>();
        visitedCells.add(board.getRandomCell());

        while (visitedCells.size() < board.getCells().size()) {
            Cell randomCell = this.getRandomUnvisitedCell(board, visitedCells);
            ArrayList<Cell> path = new ArrayList<>();

            path.add(randomCell);
            while (!visitedCells.contains(randomCell)) {
                ArrayList<Cell> neighbourCells = board.getNeighbourCells(randomCell);
                randomCell = neighbourCells.get((int) (Math.random() * neighbourCells.size()));
                if (path.contains(randomCell)) {
                    path.subList(path.indexOf(randomCell), path.size()).clear();
                    path.add(randomCell);
                } else if (visitedCells.contains(randomCell)) {
                    path.add(randomCell);
                } else {
                    path.add(randomCell);
                }
            }
            drawPath(path);
            visitedCells.addAll(path);
        }
    }

    private void drawPath(ArrayList<Cell> path) {
        for (int i = 1; i < path.size(); i++) {
            path.get(i - 1).removeInterWall(path.get(i));
        }
    }

    private Cell getRandomUnvisitedCell(Board board, Set<Cell> visitedCells) {
        Cell cell;
        while (visitedCells.contains(cell = board.getRandomCell())) ;
        return cell;
    }
}
