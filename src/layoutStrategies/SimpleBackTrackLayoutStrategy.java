package layoutStrategies;

import interfaces.ILayoutStrategy;
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
    public void layoutBoard(Board board) {
        ArrayList<Integer> visitedCellIndices = new ArrayList<>();
        Stack<Cell> stack = new Stack<>();
        stack.push(board.getRandomCell());

        while (!stack.empty()) {
            Cell cell = stack.pop();
            visitedCellIndices.add(cell.getIndex());
            ArrayList<Cell> neighbourCells = board.getNeighbourCells(cell);

            while (neighbourCells.size() != 0) {
                Cell randomCell = neighbourCells.get((int) (Math.random() * neighbourCells.size()));
                if (!visitedCellIndices.contains(randomCell.getIndex())) {
                    cell.removeInterWall(randomCell);

                    stack.push(cell);
                    visitedCellIndices.add(randomCell.getIndex());
                    stack.push(randomCell);
                    break;
                } else {
                    neighbourCells.remove(randomCell);
                }
            }
        }
    }
}
