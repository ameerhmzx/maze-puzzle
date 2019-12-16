package solutionStrategies;

import enums.Direction;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

public interface ISolutionStrategy {
    ArrayList<Direction> solve(Cell currentCell, Cell finishCell, Board board);
}