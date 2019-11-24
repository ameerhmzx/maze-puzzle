package objects;

import enums.CellWall;
import enums.Direction;
import enums.GameState;
import interfaces.Constants;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import renderer.GameEngine;


public class Player implements Constants {
    private Rectangle rect;
    private Board board;
    private int[] location; //(x,y)

    public Player(Board board) {
        getShape();
        this.board = board;
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

    public void movePlayer(Direction direction) {
        Cell currCell = board.getCell(getPixelLocation()[1], getPixelLocation()[0]);
        Cell proceedingCell = board.getCell(currCell, direction);

        if (currCell.equals(board.getLastCell()) && direction == Direction.RIGHT) {
            GameEngine.setGameState(GameState.WON);
            move(Direction.RIGHT);
            return;
        }
        switch (direction) {
            case UP:
                if (!currCell.hasWall(CellWall.TOP)) {
                    move(direction);
                    if (proceedingCell.hasWall(CellWall.RIGHT) && proceedingCell.hasWall(CellWall.LEFT))
                        movePlayer(direction);
                }
                break;
            case DOWN:
                if (!currCell.hasWall(CellWall.BOTTOM)) {
                    move(direction);
                    if (proceedingCell.hasWall(CellWall.RIGHT) && proceedingCell.hasWall(CellWall.LEFT))
                        movePlayer(direction);
                }
                break;
            case LEFT:
                if (!currCell.hasWall(CellWall.LEFT)) {
                    move(direction);
                    if (proceedingCell.hasWall(CellWall.TOP) && proceedingCell.hasWall(CellWall.BOTTOM))
                        movePlayer(direction);
                }
                break;
            case RIGHT:
                if (!currCell.hasWall(CellWall.RIGHT)) {
                    move(direction);
                    if (proceedingCell.hasWall(CellWall.TOP) && proceedingCell.hasWall(CellWall.BOTTOM))
                        movePlayer(direction);
                }
                break;
        }
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
        System.out.println(getPixelLocation()[0] + ", " + getPixelLocation()[1] + " :: new Position");
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
    public int[] getPixelLocation(){
        return new int[]{location[0] / PIXEL_SIZE, location[1] / PIXEL_SIZE};
    }
}
