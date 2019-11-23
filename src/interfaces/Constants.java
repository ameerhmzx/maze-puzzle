package interfaces;

import javafx.scene.paint.Color;

import java.util.HashMap;

public interface Constants {
    String APP_NAME = "Maze Puzzle";
    int PIXEL_SIZE = 20;
    int CHARACTER_SIZE = 12;
    float ANIMATION_RATE = .1f;
    int MAZE_PADDING = 5;
    int DEFAULT_MAZE_SIZE = 10;
    boolean DEFAULT_WINDOW_MAXIMIZED = true;

    String[] SIZE_LIST = {"10 x 10" , "20 x 20", "50 x 50"};
    HashMap<String, Integer> SIZE_OF_GAME = new HashMap<String, Integer>(){{
        put(SIZE_LIST[0], 10);
        put(SIZE_LIST[1], 20);
        put(SIZE_LIST[2], 50);
    }};

    String BORDER_COLOR = "#333";
    int BORDER_WIDTH = 1;
    Color PLAYER_COLOR = Color.color(.09, .46, .82);
}
