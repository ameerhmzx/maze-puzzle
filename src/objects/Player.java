package objects;

import enums.Direction;
import interfaces.Constants;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;


public class Player implements Constants {
    private Rectangle rect;
    private int[] location; //(x,y)

    public Player() {
        getShape();
    }

    public Rectangle getShape() {
        if (rect == null) {
            rect = new Rectangle(PIXEL_SIZE - 8, PIXEL_SIZE - 8, PLAYER_COLOR);
            rect.relocate(4, 4);

            // Causes jerky transition
            // rect.setArcHeight(5.0);
            // rect.setArcWidth(5.0);
            location = new int[]{0, 0};
        }
        return rect;
    }

    public void reset() {
        rect.relocate(0, 0);
        location = new int[]{0, 0};
        move(0, 0);
    }

    protected void move(Direction direction) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(ANIMATION_RATE), rect);
        switch (direction) {
            case UP:
                tt.setToY(location[1] - PIXEL_SIZE);
                location[1] -= PIXEL_SIZE;
                break;
            case LEFT:
                tt.setToX(location[0] - PIXEL_SIZE);
                location[0] -= PIXEL_SIZE;
                break;
            case RIGHT:
                tt.setToX(location[0] + PIXEL_SIZE);
                location[0] += PIXEL_SIZE;
                break;
            case DOWN:
                tt.setToY(location[1] + PIXEL_SIZE);
                location[1] += PIXEL_SIZE;
                break;
        }
        System.out.println(location[0] + ", " + location[1] + " :: new Position");
        tt.play();
    }

    protected void move(int x, int y) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(ANIMATION_RATE), rect);
        tt.setToX(x);
        tt.setToY(y);
        tt.play();
    }

    public int[] getLocation() {
        return location;
    }
}
