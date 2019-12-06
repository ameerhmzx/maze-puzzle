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
public class EllerLayoutStrategy implements ILayoutStrategy {
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
            this.visitRow(y, board, ellerSets);
            this.visitNextRow(y, board, ellerSets);
        }

        this.visitRow(board.getHeight() - 1, true, board, ellerSets);

        return layoutChanges;
    }

    private HashSet<Cell> getSetFromCell(Cell cell, ArrayList<HashSet<Cell>> ellerSets) {
        for (HashSet<Cell> set : ellerSets) {
            if (set.contains(cell))
                return set;
        }
        return null;
    }

    private void joinCellSets(Cell cell1, Cell cell2, ArrayList<HashSet<Cell>> ellerSets) {
        HashSet<Cell> set1 = this.getSetFromCell(cell1, ellerSets);
        HashSet<Cell> set2 = this.getSetFromCell(cell2, ellerSets);

        if (set1 == null && set2 == null) {
            ellerSets.add(new HashSet<>() {{
                add(cell1);
                add(cell2);
            }});
        } else if (set1 == null) {
            set2.add(cell1);
        } else if (set2 == null) {
            set1.add(cell2);
        } else {
            set1.addAll(set2);
            ellerSets.remove(set2);
        }
    }

    private boolean isFromSameSet(Cell cell1, Cell cell2, ArrayList<HashSet<Cell>> ellerSets) {
        HashSet<Cell> set1 = this.getSetFromCell(cell1, ellerSets);
        HashSet<Cell> set2 = this.getSetFromCell(cell2, ellerSets);

        if (set2 != null && set1 != null) {
            return set1.equals(set2);
        }
        return false;
    }

    private void visitRow(Integer index, Board board, ArrayList<HashSet<Cell>> ellerSets) {
        visitRow(index, false, board, ellerSets);
    }

    private void visitRow(Integer index, boolean mergeAll, Board board, ArrayList<HashSet<Cell>> ellerSets) {
        for (int x = 1; x < board.getWidth(); x++) {
            Cell cell1 = board.getCell(index, x - 1);
            Cell cell2 = board.getCell(index, x);

            if (this.isFromSameSet(cell1, cell2, ellerSets)) {
                continue;
            }

            if (Math.random() > 0.5 || mergeAll) {
                cell1.removeInterWall(cell2);
                this.joinCellSets(cell1, cell2, ellerSets);
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

    private void visitNextRow(Integer index, Board board, ArrayList<HashSet<Cell>> ellerSets) {
        for (HashSet<Cell> set : ellerSets) {
            ArrayList<Cell> setCells = new ArrayList<>(set) {{
                Collections.shuffle(this);
                removeIf(e -> e.getLocation().y != index);
            }};

            int n = 1 + (int) (Math.random() * (setCells.size() - 1));
            for (int i = 0; i < n; i++) {
                int cellIndex = setCells.get(i).getIndex();
                board.getCell(cellIndex).removeInterWall(board.getCell(cellIndex + board.getWidth()));
                set.add(board.getCell(cellIndex + board.getWidth()));
            }
        }
    }
}

