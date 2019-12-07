package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Eller's Maze Generation Algorithm
 * http://weblog.jamisbuck.org/2010/12/29/maze-generation-eller-s-algorithm
 * <p>
 * Generates mazes of infinite sizes in linear time
 */
public class EllerLayoutStrategy extends PathSetLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<HashSet<Cell>> ellerSets = new ArrayList<>();

        for (int x = 0; x < board.getWidth(); x++) {
            int finalX = x;
            ellerSets.add(new HashSet<>() {{
                add(board.getCell(0, finalX));
            }});
        }

        for (int y = 0; y < board.getHeight() - 1; y++) {
            this.visitRow(y, board, ellerSets, layoutChanges);
            this.visitNextRow(y, board, ellerSets, layoutChanges);
        }

        this.visitRow(board.getHeight() - 1, true, board, ellerSets, layoutChanges);

        return layoutChanges;
    }

    private void visitRow(Integer index, Board board, ArrayList<HashSet<Cell>> ellerSets, LayoutChanges layoutChanges) {
        visitRow(index, false, board, ellerSets, layoutChanges);
    }

    private void visitRow(Integer index, boolean mergeAll, Board board, ArrayList<HashSet<Cell>> ellerSets, LayoutChanges layoutChanges) {
        for (int x = 1; x < board.getWidth(); x++) {
            Cell cell1 = board.getCell(index, x - 1);
            Cell cell2 = board.getCell(index, x);

            if (this.isFromSameSet(cell1, cell2, ellerSets)) {
                continue;
            }

            if (Math.random() > 0.5 || mergeAll) {
                cell1.removeInterWall(cell2);
                this.joinCellSets(cell1, cell2, ellerSets);
                layoutChanges.add(LayoutChange.MOVE, cell1, cell1.getInterWall(cell2));
            } else if (this.getSetFromCell(cell1, ellerSets) == null) {
                ellerSets.add(new HashSet<>() {{
                    add(cell1);
                }});
            } else if (this.getSetFromCell(cell2, ellerSets) == null) {
                ellerSets.add(new HashSet<>() {{
                    add(cell2);
                }});
            }
        }
    }

    private void visitNextRow(Integer index, Board board, ArrayList<HashSet<Cell>> ellerSets, LayoutChanges layoutChanges) {
        for (HashSet<Cell> set : ellerSets) {
            ArrayList<Cell> setCells = new ArrayList<>(set) {{
                Collections.shuffle(this);
                removeIf(e -> e.getLocation().y != index);
            }};

            int n = 1 + (int) (Math.random() * (setCells.size() - 1));
            for (int i = 0; i < n; i++) {
                int cellIndex = setCells.get(i).getIndex();
                Cell nextCell = board.getCell(cellIndex + board.getWidth());
                board.getCell(cellIndex).removeInterWall(nextCell);
                set.add(nextCell);
                layoutChanges.add(LayoutChange.MOVE, board.getCell(cellIndex), board.getCell(cellIndex).getInterWall(nextCell));
            }
        }
    }
}

