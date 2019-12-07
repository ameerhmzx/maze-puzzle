package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.HashSet;

public class KruskalMazeLayoutStrategy extends PathSetLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<HashSet<Cell>> pathSets = new ArrayList<>();
        for (Cell cell : board.getCells()) {
            pathSets.add(new HashSet<>() {{
                add(cell);
            }});
        }

        while (pathSets.size() > 1) {
            Cell randomCell = board.getRandomCell();
            ArrayList<Cell> neighbours = board.getNeighbourCells(randomCell);
            Cell randomNeighbour = neighbours.get((int) (Math.random() * neighbours.size()));

            if (this.isFromSameSet(randomCell, randomNeighbour, pathSets)) continue;
            randomCell.removeInterWall(randomNeighbour);
            layoutChanges.add(LayoutChange.MOVE, randomCell, randomCell.getInterWall(randomNeighbour));
            this.joinCellSets(randomCell, randomNeighbour, pathSets);
        }
        return layoutChanges;
    }
}
