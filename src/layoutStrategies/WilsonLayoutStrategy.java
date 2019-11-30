package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
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
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        Set<Cell> visitedCells = new HashSet<>();
        Cell cell = board.getRandomCell();
        visitedCells.add(cell);
        layoutChanges.add(LayoutChange.SET_CURRENT_CELL, cell);

        while (visitedCells.size() < board.getCells().size()) {
            Cell randomCell = this.getRandomUnvisitedCell(board, visitedCells);
            layoutChanges.add(LayoutChange.TOUCH_CELL, randomCell);
            ArrayList<Cell> path = new ArrayList<>();

            path.add(randomCell);
            while (!visitedCells.contains(randomCell)) {
                ArrayList<Cell> neighbourCells = board.getNeighbourCells(randomCell);
                randomCell = neighbourCells.get((int) (Math.random() * neighbourCells.size()));

                if (path.contains(randomCell)) {
                    ArrayList<Cell> pathCells = new ArrayList<>(path.subList(path.indexOf(randomCell), path.size()));
                    layoutChanges.add(LayoutChange.UNTOUCH_ALL, pathCells);
                    path.subList(path.indexOf(randomCell), path.size()).clear();
                    layoutChanges.add(LayoutChange.TOUCH_CELL, randomCell);
                    path.add(randomCell);
                } else {
                    layoutChanges.add(LayoutChange.TOUCH_CELL, randomCell);
                    path.add(randomCell);
                }
            }
            drawPath(path, layoutChanges);
            visitedCells.addAll(path);
        }
        return layoutChanges;
    }

    private void drawPath(ArrayList<Cell> path, LayoutChanges layoutChanges) {
        for (int i = 1; i < path.size(); i++) {
            path.get(i - 1).removeInterWall(path.get(i));
            layoutChanges.add(LayoutChange.MOVE, path.get(i - 1), path.get(i - 1).getInterWall(path.get(i)));
        }
    }

    private Cell getRandomUnvisitedCell(Board board, Set<Cell> visitedCells) {
        Cell cell;
        while (visitedCells.contains(cell = board.getRandomCell())) ;
        return cell;
    }
}
