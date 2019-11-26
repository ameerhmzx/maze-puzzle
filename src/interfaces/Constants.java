package interfaces;

import enums.CellWall;
import enums.Direction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.HashMap;

public interface Constants {
    String APP_NAME = "Maze Puzzle";
    int PIXEL_SIZE = 20;
    int CHARACTER_SIZE = 12;
    float ANIMATION_RATE = .1f;
    int MAZE_PADDING = 10;
    int DEFAULT_MAZE_WIDTH = 13;
    int DEFAULT_MAZE_HEIGHT = 8;
    boolean DEFAULT_WINDOW_MAXIMIZED = true;

    String[] SIZE_LIST = {"10 x 10", "15 x 15" , "20 x 20", "30 x 30"};
    HashMap<String, Integer> SIZE_OF_GAME = new HashMap<String, Integer>(){{
        put(SIZE_LIST[0], 10);
        put(SIZE_LIST[1], 15);
        put(SIZE_LIST[2], 20);
        put(SIZE_LIST[3], 30);
    }};

    HashMap<KeyCode, Direction> DIR_CORR_KC = new HashMap<KeyCode, Direction>(){{
        put(KeyCode.UP, Direction.UP);
        put(KeyCode.DOWN, Direction.DOWN);
        put(KeyCode.LEFT, Direction.LEFT);
        put(KeyCode.RIGHT, Direction.RIGHT);
    }};

    HashMap<Direction, CellWall> CELLWALL_CORR_DIR = new HashMap<Direction, CellWall>(){{
        put(Direction.UP, CellWall.TOP);
        put(Direction.DOWN, CellWall.BOTTOM);
        put(Direction.LEFT, CellWall.LEFT);
        put(Direction.RIGHT, CellWall.RIGHT);
    }};

    HashMap<Direction, CellWall[]> CELLS_PER_DIR = new HashMap<Direction, CellWall[]>(){{
        put(Direction.UP, new CellWall[]{CellWall.LEFT, CellWall.RIGHT});
        put(Direction.DOWN, new CellWall[]{CellWall.LEFT, CellWall.RIGHT});
        put(Direction.LEFT, new CellWall[]{CellWall.TOP, CellWall.BOTTOM});
        put(Direction.RIGHT, new CellWall[]{CellWall.TOP, CellWall.BOTTOM});
    }};

    String BORDER_COLOR = "#333";
    int BORDER_WIDTH = 1;
    Color PLAYER_COLOR = Color.color(.09, .46, .82);
}
