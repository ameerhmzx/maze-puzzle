package layoutStrategies;

import enums.Direction;
import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class SidewinderLayoutStrategy implements ILayoutStrategy {
    @Override
    public LayoutChanges layoutBoard(Board board) {
        LayoutChanges layoutChanges = new LayoutChanges();

        for (int i = 1; i < board.getWidth(); i++) {
            board.getCell(0, i).removeInterWall(board.getCell(0, i - 1));
            layoutChanges.add(LayoutChange.MOVE, board.getCell(0, i), board.getCell(0, i).getInterWall(board.getCell(0, i - 1)));
        }

        for (int i = 1; i < board.getHeight(); i++) {
            this.visitRow(i, board, layoutChanges);
        }
        return layoutChanges;
    }

    private void visitRow(int y, Board board, LayoutChanges layoutChanges) {
        int joinedCells = 0;
        for (int i = 1; i < board.getWidth(); i++) {
            if (Math.random() > .5) {
                board.getCell(y, i - 1).removeInterWall(board.getCell(y, i));
                layoutChanges.add(LayoutChange.MOVE, board.getCell(y, i - 1), board.getCell(y, i - 1).getInterWall(board.getCell(y, i)));
                joinedCells++;
            } else {
                int h = ThreadLocalRandom.current().nextInt(i - 1 - joinedCells, i);
                Cell cell = board.getCell(y, h);
                cell.removeInterWall(board.getNeighbourCell(cell, Direction.UP));
                layoutChanges.add(LayoutChange.MOVE, cell, Direction.UP);
                joinedCells = 0;
            }
        }

        int h = ThreadLocalRandom.current().nextInt(board.getWidth() - joinedCells - 1, board.getWidth());
        Cell cell = board.getCell(y, h);
        cell.removeInterWall(board.getNeighbourCell(cell, Direction.UP));
        layoutChanges.add(LayoutChange.MOVE, cell, Direction.UP);
    }
}
