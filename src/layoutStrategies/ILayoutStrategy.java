package layoutStrategies;

import layoutChanges.LayoutChanges;
import objects.Board;

public interface ILayoutStrategy {
    LayoutChanges layoutBoard(Board board);
}
