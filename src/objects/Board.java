package objects;

import enums.Direction;

import java.util.ArrayList;

public class Board {
    private ArrayList<Cell> cells;
    private int width;
    private int height;

    public Board(int width, int height) {
        this.cells = new ArrayList<>();
        this.height = height;
        this.width = width;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells.add(new Cell(x, y, y * width + x));
            }
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public ArrayList<Cell> getRow(int n) {
        ArrayList<Cell> row = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            row.add(getCell(n, x));
        }
        return row;
    }

    public Cell getCell(int n) {
        return cells.get(n);
    }

    public Cell getCell(int y, int x) {
        return cells.get(y * this.width + x);
    }

    public Cell getLastCell() {
        return getCell(height - 1, width - 1);
    }

    public Cell getRandomCell() {
        return cells.get((int) (Math.random() * this.width * this.height));
    }


    // TODO: remove redundant code between getNeighbourCell() & getNeighbourCells()
    public Cell getNeighbourCell(Cell currCell, Direction direction) {

        int cellIndex = currCell.getIndex();

        switch (direction) {
            case UP:
                // check top most row
                if (cellIndex >= this.width)
                    return cells.get(cellIndex - this.width);
                break;
            case DOWN:
                // check bottom most row
                if (cellIndex < cells.size() - width)
                    return (cells.get(cellIndex + this.width));
                break;
            case LEFT:
                // check left most column
                if (cellIndex % this.width != 0)
                    return (cells.get(cellIndex - 1));
                break;
            case RIGHT:
                // check right most column
                if ((cellIndex + 1) % this.width != 0)
                    return cells.get(cellIndex + 1);
                break;
        }
        return currCell;
    }

    public ArrayList<Cell> getNeighbourCells(Cell cell) {
        int cellIndex = cell.getIndex();
        ArrayList<Cell> neighbourCells = new ArrayList<>();

        // check top most row
        if (cellIndex >= this.width) {
            neighbourCells.add(cells.get(cellIndex - this.width));
        }

        // check right most column
        if ((cellIndex + 1) % this.width != 0) {
            neighbourCells.add(cells.get(cellIndex + 1));
        }

        // check left most column
        if (cellIndex % this.width != 0) {
            neighbourCells.add(cells.get(cellIndex - 1));
        }

        // check bottom most row
        if (cellIndex < cells.size() - width) {
            neighbourCells.add(cells.get(cellIndex + this.width));
        }

        return neighbourCells;
    }

    public ArrayList<Cell> getVisitableNeighbourCells(Cell cell) {
        ArrayList<Cell> neighbourCells = new ArrayList<>(getNeighbourCells(cell));
        neighbourCells.removeIf(neighbourCell -> neighbourCell.hasWall(cell));
        return neighbourCells;
    }

    public ArrayList<Cell> getDeadEnds() {
        ArrayList<Cell> deadEnds = new ArrayList<>();
        for (Cell cell : this.getCells()) {
            if(this.getVisitableNeighbourCells(cell).size() == 1)
                deadEnds.add(cell);
        }
        return deadEnds;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return this.width * this.height;
    }
}
