package objects;

import enums.LayoutStrategy;
import objects.Board;

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
}
