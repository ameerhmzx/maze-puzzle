package objects;

import Helpers.Constants;
import enums.Direction;
import layoutChanges.LayoutChanges;
import layoutStrategies.LayoutStrategy;
import solutionStrategies.SolutionStrategy;

import java.util.ArrayList;

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
        LayoutChanges boardChanges = layoutStrategy.layoutBoard(this.board);
        // TODO: animate board changes
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(4, 4, LayoutStrategy.BINARY_TREE);
        drawBoard(puzzle.board);
        ArrayList<Direction> solution = puzzle.solve(puzzle.board.getCell(0), SolutionStrategy.RECURSIVE_BACK_TRACK);
        for (Direction dir : solution) {
            System.out.printf("%s, ",dir);
        }
    }

    private static void drawBoard(Board board) {
        for (int x = 0; x < board.getWidth() * 2; x++) {
            Cell cell = board.getCell(0, x / 2);
            if (x % 2 == 0) {
                System.out.print(x == 0 ? "╔" : cell.hasWall(Direction.LEFT) ? "╤" : "═");
            } else {
                System.out.print(cell.hasWall(Direction.UP) ? "═══" : "   ");
                if (x / 2 == board.getWidth() - 1) {
                    System.out.print("╗\n");
                }
            }
        }

        for (int y = 1; y < board.getHeight() * 2; y++) {
            for (int x = 0; x < board.getWidth() * 2; x++) {
                Cell cell = board.getCell(y / 2, x / 2);

                if (y % 2 != 0 && x % 2 == 0) {
                    System.out.print(cell.hasWall(Direction.LEFT) ? x == 0 ? "║" : "│" : " ");
                } else if (y % 2 != 0) {
                    System.out.print("   ");
                    if (x / 2 == board.getWidth() - 1) {
                        System.out.print(cell.hasWall(Direction.RIGHT) ? "║\n" : " \n");
                    }
                } else if (x % 2 == 0) {
                    System.out.print(x == 0 ? cell.hasWall(Direction.UP) ? "╟" : "║" : "┼");
                } else {
                    System.out.print(cell.hasWall(Direction.UP) ? "───" : "   ");
                    if (x / 2 == board.getWidth() - 1) {
                        System.out.print(cell.hasWall(Direction.UP) ? "╢\n" : "║\n");
                    }
                }
            }
        }

        for (int x = 0; x < board.getWidth() * 2; x++) {
            Cell cell = board.getCell(board.getHeight() - 1, x / 2);
            if (x % 2 == 0) {
                System.out.print(x == 0 ? "╚" : cell.hasWall(Direction.LEFT) ? "╧" : "═");
            } else {
                System.out.print(cell.hasWall(Direction.DOWN) ? "═══" : "   ");
                if (x / 2 == board.getWidth() - 1) {
                    System.out.print("╝\n");
                }
            }
        }
    }

    public ArrayList<Direction> solve(Cell currentCell, SolutionStrategy strategy) {
        return strategy.solve(currentCell, this.board.getCell(this.getSize() - 1), board);
    }

    public int getSize() {
        return this.board.getSize();
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
}

