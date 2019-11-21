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
    private ArrayList<Integer> visitedCellIndices;
    private Stack<Cell> stack;
    private Cell currentCell;
    private Board board;

    @Override
    public void layoutBoard(Board board) {
        this.board = board;
        this.visitedCellIndices = new ArrayList<>();
        this.stack = new Stack<>();

        stack.push(board.getRandomCell());

        while (!stack.empty()) {
            this.visitCell(stack.pop());
        }
    }

    private void visitCell(Cell cell) {
        this.visitedCellIndices.add(cell.getIndex());
        ArrayList<Cell> neighbourCells = board.getNeighbourCells(cell);

        for (Cell neighbourCell : neighbourCells) {
            if(this.isCellUnvisited(neighbourCell)){
                stack.push(cell);

                break;
            }
        }
    }

    private boolean isCellUnvisited(Cell cell) {
        return visitedCellIndices.contains(cell.getIndex());
    }
}
