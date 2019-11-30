package objects;

import Helpers.Constants;
import enums.Direction;
import layoutStrategies.LayoutStrategy;

public class Puzzle implements Constants {
    private LayoutStrategy layoutStrategy;
    private Board board;
    private int width;
    private int height;

    public Puzzle(int width, int height, LayoutStrategy layoutStrategy) {
        this.width = width;
        this.height = height;
        this.layoutStrategy = layoutStrategy;

        this.board = new Board(width, height);
        drawBoard(this.board);
        layoutStrategy.layoutBoard(this.board).display();
        drawBoard(this.board);
    }

    public static void main(String[] args) {
        new Puzzle(6, 6, LayoutStrategy.WILSON_MAZE);
    }

    public int getSize() {
        return this.width * this.height;
    }

    public LayoutStrategy getLayoutStrategy() {
        return layoutStrategy;
    }

    public Board getBoard() {
        return board;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private void drawBoard(Board board) {
        for (int x = 0; x < width * 2; x++) {
            Cell cell = board.getCell(0, x / 2);
            if (x % 2 == 0) {
                System.out.print(x == 0 ? "╔" : cell.hasWall(Direction.LEFT) ? "╤" : "═");
            } else {
                System.out.print(cell.hasWall(Direction.UP) ? "═══" : "   ");
                if (x / 2 == this.width - 1) {
                    System.out.print("╗\n");
                }
            }
        }

        for (int y = 1; y < height * 2; y++) {
            for (int x = 0; x < width * 2; x++) {
                Cell cell = board.getCell(y / 2, x / 2);

                if (y % 2 != 0 && x % 2 == 0) {
                    System.out.print(cell.hasWall(Direction.LEFT) ? x == 0 ? "║" : "│" : " ");
                } else if (y % 2 != 0) {
                    System.out.print("   ");
                    if (x / 2 == this.width - 1) {
                        System.out.print(cell.hasWall(Direction.RIGHT) ? "║\n" : " \n");
                    }
                } else if (x % 2 == 0) {
                    System.out.print(x == 0 ? cell.hasWall(Direction.UP) ? "╟" : "║" : "┼");
                } else {
                    System.out.print(cell.hasWall(Direction.UP) ? "───" : "   ");
                    if (x / 2 == this.width - 1) {
                        System.out.print(cell.hasWall(Direction.UP) ? "╢\n" : "║\n");
                    }
                }
            }
        }

        for (int x = 0; x < width * 2; x++) {
            Cell cell = board.getCell(height - 1, x / 2);
            if (x % 2 == 0) {
                System.out.print(x == 0 ? "╚" : cell.hasWall(Direction.LEFT) ? "╧" : "═");
            } else {
                System.out.print(cell.hasWall(Direction.DOWN) ? "═══" : "   ");
                if (x / 2 == this.width - 1) {
                    System.out.print("╝\n");
                }
            }
        }
    }
}

