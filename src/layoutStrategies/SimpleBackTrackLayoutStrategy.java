package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Stack based implementation of recursive backtrack maze generation algorithm
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
 */
public class SimpleBackTrackLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<Cell> visitedCells = new ArrayList<>();
        Stack<Cell> stack = new Stack<>();
        stack.push(board.getRandomCell());
        layoutChanges.add(LayoutChange.SET_CURRENT_CELL, stack.get(0));


        while (!stack.empty()) {
            Cell cell = stack.pop();
            visitedCells.add(cell);
            ArrayList<Cell> neighbourCells = board.getNeighbourCells(cell);
            neighbourCells.removeAll(visitedCells);

            if (neighbourCells.size() > 0) {
                Cell randomCell = neighbourCells.get((int) (Math.random() * neighbourCells.size()));
                if (!visitedCells.contains(randomCell)) {
                    cell.removeInterWall(randomCell);
                    layoutChanges.add(LayoutChange.MOVE, cell, randomCell.getInterWall(cell));
                    layoutChanges.add(LayoutChange.TOUCH_CELL, randomCell);

                    stack.push(cell);
                    visitedCells.add(randomCell);
                    stack.push(randomCell);
                } else {
                    neighbourCells.remove(randomCell);
                }
            } else {
                layoutChanges.add(LayoutChange.UNTOUCH_CELL, cell);
            }
        }
        return layoutChanges;
    }
}
