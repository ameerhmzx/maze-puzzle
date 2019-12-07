package layoutStrategies;

import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;
import java.util.Collections;

public enum PostLayoutStrategy implements ILayoutStrategy {
    PERFECT_MAZE("Perfect Maze") {
        public LayoutChanges layoutBoard(Board board) {
            return new LayoutChanges();
        }
    },
    LOOPING_MAZE("Random Loops") {
        public LayoutChanges layoutBoard(Board board) {
            return this.braidMaze(board, Math.random());
        }
    },
    BRAID_MAZE("Braid Maze") {
        public LayoutChanges layoutBoard(Board board) {
            return this.braidMaze(board, 1);
        }
    };

    private String name;

    PostLayoutStrategy(String name) {
        this.name = name;
    }

    public static PostLayoutStrategy getFromName(String name) {
        for (PostLayoutStrategy strategy : PostLayoutStrategy.values()) {
            if (strategy.getName().equals(name))
                return strategy;
        }
        throw new IllegalArgumentException(name);
    }

    public String getName() {
        return name;
    }

    public LayoutChanges braidMaze(Board board, double braidFactor) {
        LayoutChanges layoutChanges = new LayoutChanges();
        ArrayList<Cell> deadEnds = board.getDeadEnds();
        for (Cell deadEnd : deadEnds) {
            if (Math.random() < braidFactor) {
                ArrayList<Cell> neighbours = board.getNeighbourCells(deadEnd);
                Collections.shuffle(neighbours);

                layoutChanges.add(LayoutChange.MOVE, deadEnd, deadEnd.getInterWall(neighbours.get(0)));
                deadEnd.removeInterWall(neighbours.get(0));
            }
        }

        return layoutChanges;
    }
}
