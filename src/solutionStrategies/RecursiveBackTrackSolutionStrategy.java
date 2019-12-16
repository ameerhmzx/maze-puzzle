package solutionStrategies;

import enums.Direction;
import objects.Board;
import objects.Cell;

import java.util.ArrayList;

class RecursiveBackTrackSolutionStrategy implements ISolutionStrategy {
    @Override
    public ArrayList<Direction> solve(Cell currentCell, Cell finishCell, Board board) {
        ArrayList<Direction> moves = new ArrayList<>();
        ArrayList<Cell> visitedCells = new ArrayList<>();
        visitedCells.add(currentCell);
        visitCell(currentCell, finishCell, board, moves, visitedCells);
        return moves;
    }

    private boolean visitCell(Cell currentCell, Cell finishCell, Board board, ArrayList<Direction> moves, ArrayList<Cell> visitedCells) {
        if (currentCell.equals(finishCell)) return true;
        ArrayList<Cell> visitableCells = board.getVisitableNeighbourCells(currentCell);

        while (visitableCells.size() > 0) {
            Cell randomCell = visitableCells.get((int) (Math.random() * visitableCells.size()));
            if (visitedCells.contains(randomCell)) {
                visitableCells.remove(randomCell);
                continue;
            }
            visitedCells.add(randomCell);
            moves.add(currentCell.getInterWall(randomCell));

            boolean isFinished = visitCell(randomCell, finishCell, board, moves, visitedCells);
            if (isFinished)
                return true;
            else {
                visitableCells.remove(randomCell);
                moves.add(randomCell.getInterWall(currentCell));
            }
        }
        return false;
    }
}