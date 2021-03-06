package objects;

import Helpers.Constants;
import enums.Direction;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Cell implements Constants {

    public Point getLocation() {
        return location;
    }

    private java.awt.Point location;
    private int index;
    private Map<Direction, Boolean> walls;

    public Cell(int x, int y, int index) {
        this.index = index;
        this.walls = new HashMap<>();
        this.location = new Point(x, y);

        for (Direction wall : Direction.values()) {
            this.walls.put(wall, true);
        }
    }

    public Map<Direction, Boolean> getWalls() {
        return walls;
    }

    public int getIndex() {
        return this.index;
    }

    public void setWall(Direction wall, boolean val) {
        this.walls.put(wall, val);
    }

    public boolean hasWall(Direction wall) {
        return this.walls.get(wall);
    }

    public boolean hasWall(Cell cell) {
        Direction interWall = getInterWall(cell);
        return hasWall(interWall);
    }

    public void removeWall(Direction wall) {
        this.walls.put(wall, false);
    }

    public Direction getInterWall(Cell cell) {
        Direction interWall;
        if (cell.getIndex() == this.getIndex() + 1) {
            interWall = Direction.RIGHT;
        } else if (cell.getIndex() == this.getIndex() - 1) {
            interWall = Direction.LEFT;
        } else if (cell.getIndex() < this.getIndex()) {
            interWall = Direction.UP;
        } else {
            interWall = Direction.DOWN;
        }
        return interWall;
    }

    public void removeInterWall(Cell cell) {
        Direction interWall = getInterWall(cell);
        this.removeWall(interWall);
        cell.removeWall(OPPOSING_WALLS.get(interWall));
    }

    @Override
    public String toString() {
        return String.format("Cell@%d (%d,%d)", this.index, this.location.x, this.location.y);
    }

    public boolean equals(Cell cell) {
        return cell.getIndex() == this.getIndex();
    }

    @Override
    public Cell clone() {
        Cell cell = new Cell(this.location.x, this.location.y, this.index);
        for (Direction dir : this.getWalls().keySet())
            cell.setWall(dir, this.hasWall(dir));
        return cell;
    }
}