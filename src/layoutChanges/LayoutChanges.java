package layoutChanges;

import enums.Direction;
import objects.Cell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

public class LayoutChanges {
    private ArrayList<Dictionary> layoutChanges;

    public LayoutChanges() {
        this.layoutChanges = new ArrayList<>();
    }

    public void add(LayoutChange layoutChange, int width, int height) {
        if (layoutChange == LayoutChange.BOARD_CREATED)
            this.layoutChanges.add(new Hashtable() {{
                put("type", LayoutChange.BOARD_CREATED);
                put("width", width);
                put("height", height);
            }});
        else
            throw new UnsupportedOperationException(layoutChange + " not supported");
    }

    public void add(LayoutChange layoutChange, Cell cell) {
        switch (layoutChange) {
            case TOUCH_CELL:
            case UNTOUCH_CELL:
            case SET_CURRENT_CELL:
                this.layoutChanges.add(new Hashtable() {{
                    put("type", layoutChange);
                    put("cell", cell);
                }});
                break;
            default:
                throw new UnsupportedOperationException(layoutChange + " not supported");
        }
    }

    public void add(LayoutChange layoutChange, Collection<Cell> cells) {
        switch (layoutChange) {
            case UNTOUCH_ALL:
            case TOUCH_ALL:
                this.layoutChanges.add(new Hashtable() {{
                    put("type", layoutChange);
                    put("cells", cells);
                }});
                break;
            default:
                throw new UnsupportedOperationException(layoutChange + " not supported");
        }
    }

    public void add(LayoutChange layoutChange, Cell currentCell, Direction direction) {
        if (layoutChange == LayoutChange.MOVE || layoutChange == LayoutChange.PLAYER_MOVE)
            this.layoutChanges.add(new Hashtable() {{
                put("type", layoutChange);
                put("direction", direction);
                put("currentCell", currentCell);
            }});
        else
            throw new UnsupportedOperationException(layoutChange + " not supported");
    }

    public void display() {
        for (Dictionary change : this.layoutChanges) {
            switch ((LayoutChange) change.get("type")) {
                case MOVE:
                    System.out.printf("Move to %s %s\n", change.get("direction"), change.get("currentCell"));
                    break;

                case SET_CURRENT_CELL:
                    System.out.printf("Goto to %s\n", change.get("cell"));
                    break;

                case TOUCH_CELL:
                case UNTOUCH_CELL:
                    System.out.printf("%s %s\n", change.get("type"), change.get("cell"));
                    break;

                case TOUCH_ALL:
                case UNTOUCH_ALL:
                    System.out.printf("%s %s\n", change.get("type"), ((Collection<Cell>) change.get("cells")).stream().map(Cell::toString).reduce((c, d) -> c + ", " + d));
                    break;
            }
        }
    }
}
