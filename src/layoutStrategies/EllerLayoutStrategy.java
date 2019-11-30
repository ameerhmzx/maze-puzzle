package layoutStrategies;

import layoutChanges.LayoutChanges;
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
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<HashSet<Cell>> ellerSets = new ArrayList<>();
        return layoutChanges;
    }
}
