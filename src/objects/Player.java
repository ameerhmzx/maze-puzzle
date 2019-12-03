package objects;

import Helpers.Constants;
import Helpers.ScoreCounter;
import enums.Direction;
import interfaces.OnWon;
import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Player implements Constants {
    private Rectangle rect;
    private Board board;
    private int[] location; //(x,y)
    private OnWon onWon;
    private ScoreCounter scoreCounter;

    public Player(Board board, OnWon onWon) {
        getShape();
        this.board = board;
        this.onWon = onWon;
        scoreCounter = new ScoreCounter(board.getHeight()*board.getWidth());
    }

    public Rectangle getShape() {
        if (rect == null) {
            rect = new Rectangle(CHARACTER_SIZE, CHARACTER_SIZE, PLAYER_COLOR);
            rect.relocate((PIXEL_SIZE-CHARACTER_SIZE)/2, (PIXEL_SIZE-CHARACTER_SIZE)/2);

            // Causes jerky transition
            // rect.setArcHeight(5.0);
            // rect.setArcWidth(5.0);
            location = new int[]{0, 0};
        }
        return rect;
    }

    public void reset() {
        rect.relocate((PIXEL_SIZE-CHARACTER_SIZE)/2, (PIXEL_SIZE-CHARACTER_SIZE)/2);
        location = new int[]{0, 0};
        move(0, 0);
    }

    public void move(Direction direction){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(PLAYER_ANIMATION_RATE), rect);

        Cell currCell = board.getCell(getLocation()[1], getLocation()[0]);
        Cell proceedingCell = board.getNeighbourCell(currCell, direction);

        boolean parallelCell1 = proceedingCell.hasWall(PERPENDICULAR_DIRECTIONS.get(direction)[0]);
        boolean parallelCell2 = proceedingCell.hasWall(PERPENDICULAR_DIRECTIONS.get(direction)[1]);

        if (currCell.equals(board.getLastCell()) && direction == Direction.RIGHT) {
            onWon.gameWon();
            move(board.getWidth() * PIXEL_SIZE, (board.getHeight() - 1) * PIXEL_SIZE);
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
            System.out.println(getLocation()[0] + ", " + getLocation()[1] + " :: new Position");
            tt.play();
            if (parallelCell1 && parallelCell2)
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
