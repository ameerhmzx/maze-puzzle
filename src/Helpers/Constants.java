package Helpers;

import enums.Direction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
    String APP_NAME = "Maze Puzzle";
    int PIXEL_SIZE = 20;
    int CHARACTER_SIZE = 12;
    float PLAYER_ANIMATION_RATE = .1f;
    int MAZE_DRAW_ANIMATION_RATE = 500;
    int MAZE_PADDING = 10;
    int DEFAULT_MAZE_WIDTH = 30;
    int DEFAULT_MAZE_HEIGHT = 20;
    boolean DEFAULT_WINDOW_MAXIMIZED = true;
    LayoutStrategy DEFAULT_LAYOUT_STRATEGY = LayoutStrategy.RECURSIVE_BACK_TRACK;
    PostLayoutStrategy DEFAULT_POST_LAYOUT_STRATEGY = PostLayoutStrategy.PERFECT_MAZE;

    String[] SIZE_LIST = {"10", "15" , "20", "30"};

    HashMap<KeyCode, Direction> DIRECTION_CORRESPONDING_KEY_CODE = new HashMap<KeyCode, Direction>() {{
        put(KeyCode.UP, Direction.UP);
        put(KeyCode.DOWN, Direction.DOWN);
        put(KeyCode.LEFT, Direction.LEFT);
        put(KeyCode.RIGHT, Direction.RIGHT);
    }};

    HashMap<Direction, Direction[]> PERPENDICULAR_DIRECTIONS = new HashMap<Direction, Direction[]>() {{
        put(Direction.UP, new Direction[]{Direction.LEFT, Direction.RIGHT});
        put(Direction.DOWN, new Direction[]{Direction.LEFT, Direction.RIGHT});
        put(Direction.LEFT, new Direction[]{Direction.UP, Direction.DOWN});
        put(Direction.RIGHT, new Direction[]{Direction.UP, Direction.DOWN});
    }};

    Map<Direction, Direction> OPPOSING_WALLS = new HashMap<Direction, Direction>() {{
        put(Direction.LEFT, Direction.RIGHT);
        put(Direction.RIGHT, Direction.LEFT);
        put(Direction.UP, Direction.DOWN);
        put(Direction.DOWN, Direction.UP);
    }};

    String BORDER_COLOR = "#333";
    int BORDER_WIDTH = 1;
    Color PLAYER_COLOR = Color.color(.09, .46, .82);
}
