package objects;

import enums.CellWall;
import enums.LayoutStrategy;

public class Puzzle {
    private int size;
    private LayoutStrategy layoutStrategy;
    private Board board;

    public Puzzle(int size, LayoutStrategy layoutStrategy) {
        this.size = size;
        this.layoutStrategy = layoutStrategy;

        this.board = new Board(size, size);
        layoutStrategy.layoutBoard(this.board);
    }

    public int getSize() {
        return size;
    }

    public LayoutStrategy getLayoutStrategy() {
        return layoutStrategy;
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        new Puzzle(4, LayoutStrategy.SIMPLE_BACK_TRACK);
    }

    private void drawBoard(Board board) {
        for (int y = 0; y < size * 2; y++) {
            for (int x = 0; x < size * 2; x++) {
                Cell cell = board.getCell(y / 2, x / 2);

                if (y % 2 != 0 && x % 2 == 0) {
                    System.out.print(cell.hasWall(CellWall.LEFT) ? "|" : " ");
                } else if (y % 2 != 0) {
                    System.out.print("  ");
                    if (x / 2 == this.size - 1) {
                        System.out.print(cell.hasWall(CellWall.RIGHT) ? "|\n" : " \n");
                    }
                } else if (x % 2 == 0) {
                    System.out.print("+");
                } else {
                    System.out.print(cell.hasWall(CellWall.TOP) ? "--" : "  ");
                    if (x / 2 == this.size - 1) {
                        System.out.print("+\n");
                    }
                }
            }
        }

        for (int x = 0; x < size * 2; x++) {
            Cell cell = board.getCell(size - 1, x / 2);
            if (x % 2 == 0) {
                System.out.print("+");
            } else {
                System.out.print(cell.hasWall(CellWall.BOTTOM) ? "--" : "  ");
                if (x / 2 == this.size - 1) {
                    System.out.print("+\n");
                }
            }
        }
    }
}
