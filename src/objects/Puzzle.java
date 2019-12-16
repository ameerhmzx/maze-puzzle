package objects;

import Helpers.Constants;
import enums.Direction;
import layoutChanges.LayoutChanges;
import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;
import solutionStrategies.SolutionStrategy;

import java.util.ArrayList;

public class Puzzle implements Constants {
    private LayoutStrategy layoutStrategy;
    private PostLayoutStrategy postLayoutStrategies;
    private LayoutChanges layoutChanges;
    private LayoutChanges postLayoutChanges;
    private Board board;
    private int width;
    private int height;

    public Puzzle(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Board(width, height);
    }

    public Puzzle(int width, int height, LayoutStrategy layoutStrategy) {
        this.width = width;
        this.height = height;

        this.board = new Board(width, height);
        this.applyLayoutStrategy(layoutStrategy);
    }

    public Puzzle(int width, int height, PostLayoutStrategy postLayoutStrategy) {
        this.width = width;
        this.height = height;

        this.board = new Board(width, height);
        this.applyPostLayoutStrategy(postLayoutStrategy);
    }

    public Puzzle(int width, int height, LayoutStrategy layoutStrategy, PostLayoutStrategy postLayoutStrategy) {
        this.width = width;
        this.height = height;

        this.board = new Board(width, height);
        this.applyLayoutStrategy(layoutStrategy);
        this.applyPostLayoutStrategy(postLayoutStrategy);
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(6, 6);
        puzzle.applyLayoutStrategy(LayoutStrategy.RECURSIVE_BACK_TRACK);
        puzzle.applyPostLayoutStrategy(PostLayoutStrategy.BRAID_MAZE);
        drawBoard(puzzle.board);

        ArrayList<Direction> solution = puzzle.solve(puzzle.board.getCell(0), SolutionStrategy.DIJKSTRA_PATH_FINDING);
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

    public void applyLayoutStrategy(LayoutStrategy layoutStrategy) {
        this.layoutStrategy = layoutStrategy;
        this.layoutChanges = layoutStrategy.layoutBoard(this.board);
    }

    public void applyPostLayoutStrategy(PostLayoutStrategy postLayoutStrategy) {
        this.postLayoutStrategies = postLayoutStrategy;
        this.postLayoutChanges = postLayoutStrategy.layoutBoard(this.board);
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

    public PostLayoutStrategy getPostLayoutStrategies() {
        return postLayoutStrategies;
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

    public LayoutChanges getLayoutChanges() {
        return layoutChanges;
    }

    public LayoutChanges getPostLayoutChanges() {
        return postLayoutChanges;
    }
}

