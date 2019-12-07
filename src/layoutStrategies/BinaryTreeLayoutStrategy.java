package layoutStrategies;

import enums.BinaryTreeBias;
import enums.Direction;
import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

public class BinaryTreeLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();
        BinaryTreeBias bias = BinaryTreeBias.randomBias();

        for (int i = 0; i < board.getSize(); i++) {
            Cell cell = board.getCell(i);
            ArrayList<Cell> neighbourCells = board.getNeighbourCells(cell);
            neighbourCells.removeIf(e -> this.isOpposingBiasCell(cell.getInterWall(e), bias));

            if (neighbourCells.size() != 0) {
                Cell randomCell = neighbourCells.get((int) (Math.random() * neighbourCells.size()));
                cell.removeInterWall(randomCell);
                layoutChanges.add(LayoutChange.MOVE, cell, cell.getInterWall(randomCell));
            }
        }
        return layoutChanges;
    }

    private boolean isOpposingBiasCell(Direction dir, BinaryTreeBias bias) {
        return (bias.rightBias && dir == Direction.LEFT) ||
                (!bias.rightBias && dir == Direction.RIGHT) ||
                (bias.topBias && dir == Direction.DOWN) ||
                (!bias.topBias && dir == Direction.UP);
    }
}
