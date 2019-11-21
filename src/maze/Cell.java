package maze;

import java.util.HashMap;
import java.util.Map;

class Cell {
    private Point location;
    private Map<CellWall, Boolean> walls = new HashMap<>();

    private Map<CellWall, CellWall> opposingWalls = Map.of(
            CellWall.LEFT, CellWall.RIGHT,
            CellWall.RIGHT, CellWall.LEFT,
            CellWall.TOP, CellWall.BOTTOM,
            CellWall.BOTTOM, CellWall.TOP
    );

    Cell(Point location) {
        this.location = location;

        for (CellWall wall : CellWall.values()) {
            walls.put(wall, true);
        }
    }

    private void removeWall(CellWall wall) {
        this.walls.put(wall, false);
    }

    void removeInterWall(Cell cell) {
        CellWall interWall;
        if (cell.getIndex() == this.getIndex() + 1) {
            interWall = CellWall.LEFT;
        } else if (cell.getIndex() == this.getIndex() - 1) {
            interWall = CellWall.RIGHT;
        } else if (cell.getIndex() < this.getIndex()) {
            interWall = CellWall.TOP;
        } else {
            interWall = CellWall.BOTTOM;
        }
        this.removeWall(interWall);
        cell.removeWall(opposingWalls.get(interWall));
    }

    int getIndex() {
        return this.location.index;
    }
}
