package objects;

import enums.CellWall;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private static Map<CellWall, CellWall> opposingWalls = new HashMap<CellWall, CellWall>() {{
        put(CellWall.LEFT, CellWall.RIGHT);
        put(CellWall.RIGHT, CellWall.LEFT);
        put(CellWall.TOP, CellWall.BOTTOM);
        put(CellWall.BOTTOM, CellWall.TOP);
    }};

    private Point location;
    private Map<CellWall, Boolean> walls;

    Cell(Point location) {
        walls = new HashMap<>();
        this.location = location;

        for (CellWall wall : CellWall.values()) {
            walls.put(wall, true);
        }
    }

    public Map<CellWall, Boolean> getWalls() {
        return walls;
    }

    public boolean hasWall(CellWall wall) {
        return this.walls.get(wall);
    }

    private void removeWall(CellWall wall) {
        this.walls.put(wall, false);
    }

    public void removeInterWall(Cell cell) {
        CellWall interWall;
        if (cell.getIndex() == this.getIndex() + 1) {
            interWall = CellWall.RIGHT;
        } else if (cell.getIndex() == this.getIndex() - 1) {
            interWall = CellWall.LEFT;
        } else if (cell.getIndex() < this.getIndex()) {
            interWall = CellWall.TOP;
        } else {
            interWall = CellWall.BOTTOM;
        }
        this.removeWall(interWall);
        cell.removeWall(opposingWalls.get(interWall));
    }

    public int getIndex() {
        return this.location.index;
    }
}