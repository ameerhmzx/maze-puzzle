package objects;

import enums.Direction;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private static Map<Direction, Direction> opposingWalls = new HashMap<Direction, Direction>() {{
        put(Direction.LEFT, Direction.RIGHT);
        put(Direction.RIGHT, Direction.LEFT);
        put(Direction.UP, Direction.DOWN);
        put(Direction.DOWN, Direction.UP);
    }};

    private Point location;
    private Map<Direction, Boolean> walls;

    Cell(Point location) {
        walls = new HashMap<>();
        this.location = location;

        for (Direction wall : Direction.values()) {
            walls.put(wall, true);
        }
    }

    public Map<Direction, Boolean> getWalls() {
        return walls;
    }

    public int getIndex() {
        return this.location.index;
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

    private void removeWall(Direction wall) {
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
        cell.removeWall(opposingWalls.get(interWall));
    }

    @Override
    public String toString() {
        return String.format("Cell@%d (%d,%d)", this.location.index, this.location.x, this.location.y);
    }

    public boolean equals(Cell cell) {
        return cell.getIndex() == this.getIndex();
    }
}