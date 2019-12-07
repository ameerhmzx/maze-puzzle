package layoutStrategies;

import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class PathSetLayoutStrategy {
    protected HashSet<Cell> getSetFromCell(Cell cell, ArrayList<HashSet<Cell>> ellerSets) {
        for (HashSet<Cell> set : ellerSets) {
            if (set.contains(cell))
                return set;
        }
        return null;
    }

    protected void joinCellSets(Cell cell1, Cell cell2, ArrayList<HashSet<Cell>> ellerSets) {
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

    protected boolean isFromSameSet(Cell cell1, Cell cell2, ArrayList<HashSet<Cell>> ellerSets) {
        HashSet<Cell> set1 = this.getSetFromCell(cell1, ellerSets);
        HashSet<Cell> set2 = this.getSetFromCell(cell2, ellerSets);

        if (set2 != null && set1 != null) {
            return set1.equals(set2);
        }
        return false;
    }
}
