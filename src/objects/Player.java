package objects;

import Helpers.Constants;
import Helpers.Context;
import Helpers.ScoreCounter;
import enums.Direction;
import interfaces.OnWon;
import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Player implements Constants {
    private Rectangle rect;
    private int[] location; //(x,y)
    private OnWon onWon;
    private ScoreCounter scoreCounter;

    private Context context;

    public Player(Context context, OnWon onWon) {
        getShape();
        this.context = context;
        this.onWon = onWon;
        scoreCounter = new ScoreCounter(context.getBoard().getHeight() * context.getBoard().getWidth());
    }

    public Rectangle getShape() {
        if (rect == null) {
            rect = new Rectangle(CHARACTER_SIZE, CHARACTER_SIZE, PLAYER_COLOR);
            rect.relocate((PIXEL_SIZE - CHARACTER_SIZE) / 2, (PIXEL_SIZE - CHARACTER_SIZE) / 2);

            // Causes jerky transition
            // rect.setArcHeight(5.0);
            // rect.setArcWidth(5.0);
            location = new int[]{0, 0};
        }
        return rect;
    }

    public void reset() {
        rect.relocate((PIXEL_SIZE - CHARACTER_SIZE) / 2, (PIXEL_SIZE - CHARACTER_SIZE) / 2);
        location = new int[]{0, 0};
        move(0, 0);
    }

    public void move(Direction direction) {
        move(direction, true);
    }

    public void move(Direction direction, boolean forward) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(PLAYER_ANIMATION_RATE), rect);

        Cell currCell = context.getBoard().getCell(getLocation()[1], getLocation()[0]);
        Cell proceedingCell = context.getBoard().getNeighbourCell(currCell, direction);

        boolean parallelCell1 = proceedingCell.hasWall(PERPENDICULAR_DIRECTIONS.get(direction)[0]);
        boolean parallelCell2 = proceedingCell.hasWall(PERPENDICULAR_DIRECTIONS.get(direction)[1]);

        if (currCell.equals(context.getBoard().getLastCell()) && direction == Direction.RIGHT) {
            move(context.getBoard().getWidth() * PIXEL_SIZE, (context.getBoard().getHeight() - 1) * PIXEL_SIZE);
            onWon.gameWon();
            return;
        }

        if (!currCell.hasWall(direction)) {
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
            scoreCounter.increaseStep();
            context.setNumberOfMoves(context.getNumberOfMoves() + 1);
            System.out.println(getLocation()[0] + ", " + getLocation()[1] + " :: new Position");
            tt.play();
            if (parallelCell1 && parallelCell2 && forward)
                move(direction);
        }
    }
    public void move(KeyCode kc) {
        move(DIRECTION_CORRESPONDING_KEY_CODE.get(kc));
    }
    protected void move(int x, int y) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(PLAYER_ANIMATION_RATE), rect);
        tt.setToX(x);
        tt.setToY(y);
        tt.play();
    }

    public long getScore(){
        return scoreCounter.getScore();
    }

    public int[] getLocation(){
        return new int[]{location[0] / PIXEL_SIZE, location[1] / PIXEL_SIZE};
    }
}
