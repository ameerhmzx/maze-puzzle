package layoutStrategies;

import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Eller's Maze Generation Algorithm
 * http://weblog.jamisbuck.org/2010/12/29/maze-generation-eller-s-algorithm
 *
 * Generates mazes of infinite sizes in linear time
 */
public class EllerLayoutStrategy implements ILayoutStrategy {
    @Override
    public void layoutBoard(Board board) {
        ArrayList<HashSet<Cell>> ellerSets = new ArrayList<>();
    }
}
