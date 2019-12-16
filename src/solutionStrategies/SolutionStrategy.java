package solutionStrategies;

import enums.Direction;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

public enum SolutionStrategy implements ISolutionStrategy {
    RECURSIVE_BACK_TRACK("Recursive back track") {
        @Override
        public ArrayList<Direction> solve(Cell currentCell, Cell finishCell, Board board) {
            return new RecursiveBackTrackSolutionStrategy().solve(currentCell, finishCell, board);
        }
    },
    DIJKSTRA_PATH_FINDING("Dijkstra Path Finding Algorithm") {
        @Override
        public ArrayList<Direction> solve(Cell currentCell, Cell finishCell, Board board) {
            return new DijkstraSolutionStrategy().solve(currentCell, finishCell, board);
        }
    };

    private String name;

    SolutionStrategy(String name) {
        this.name = name;
    }

    public static SolutionStrategy getFromName(String name) {
        for (SolutionStrategy strategy : SolutionStrategy.values()) {
            if (strategy.getName().equals(name))
                return strategy;
        }
        throw new IllegalArgumentException(name);
    }

    public String getName() {
        return name;
    }
}
