package objects;

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
                cells.add(new Cell(new Point(x, y, y * height + x)));
            }
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public Cell getCell(int n) {
        return cells.get(n);
    }

    public Cell getCell(int y, int x) {
        return cells.get(y * this.width + x);
    }

    public Cell getRandomCell() {
        return cells.get((int) (Math.random() * this.width * this.height));
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
        if (cellIndex / this.width != this.height - 1) {
            neighbourCells.add(cells.get(cellIndex + this.height));
        }

        return neighbourCells;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
