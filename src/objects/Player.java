package objects;

import enums.Direction;
import interfaces.Constants;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Player implements Constants {
    private static Rectangle rect;
    private static int[] location; //(x,y)

    public static Rectangle getPlayer() {
        if(rect == null){
            rect = new Rectangle(PIXEL_SIZE, PIXEL_SIZE, Color.RED);
            rect.relocate(0,0);
            location = new int[]{0,0};
        }
        return rect;
    }

    public static void reset(){
        rect.relocate(0,0);
        location = new int[]{0,0};
        moveCharacter(0,0);
    }

    public static void moveCharacter(Direction direction){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(ANIMATION_RATE), rect);
        switch (direction){
            case UP:
                tt.setToY(location[1]-PIXEL_SIZE);
                location[1] -= PIXEL_SIZE;
                break;
            case LEFT:
                tt.setToX(location[0]-PIXEL_SIZE);
                location[0] -= PIXEL_SIZE;
                break;
            case RIGHT:
                tt.setToX(location[0]+PIXEL_SIZE);
                location[0] += PIXEL_SIZE;
                break;
            case DOWN:
                tt.setToY(location[1]+PIXEL_SIZE);
                location[1] += PIXEL_SIZE;
                break;
        }
        System.out.println(location[0] + ", " + location[1] + " :: new Position");
        tt.play();
    }

    public static void moveCharacter(int x, int y){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(ANIMATION_RATE), rect);
        tt.setToX(x);
        tt.setToY(y);
        tt.play();
    }


}