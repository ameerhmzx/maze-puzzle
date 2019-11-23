package renderer;

import enums.Direction;
import enums.GameState;
import interfaces.Constants;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import objects.Puzzle;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants {
    private Puzzle puzzle;
    private static GameState gameState;

    public static void main(String[] args) {
        GameEngine.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        puzzle = new Puzzle(10, LayoutStrategy.PRIM_RANDOMIZATION);
        gameState = GameState.PLAYING;

        Parent root = (new RenderEngine(puzzle)).getRoot();
        Scene scene = new Scene(root, 20*PIXEL_SIZE, 20*PIXEL_SIZE);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::kbdEventsHandler);

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void kbdEventsHandler(KeyEvent ke){
        KeyCode kc = ke.getCode();
        if((kc == UP || kc == DOWN || kc == LEFT || kc == RIGHT) && gameState == GameState.PLAYING){
            gameControls(kc);
        }else{
            switch (kc){
                case R:
                    puzzle.getPlayer().reset();
                    setGameState(GameState.PLAYING);
                    break;
            }
        }
        ke.consume();
    }

    private void gameControls(KeyCode kc){
        switch (kc) {
            case UP:
                puzzle.movePlayer(Direction.UP);
                break;
            case RIGHT:
                puzzle.movePlayer(Direction.RIGHT);
                break;
            case LEFT:
                puzzle.movePlayer(Direction.LEFT);
                break;
            case DOWN:
                puzzle.movePlayer(Direction.DOWN);
                break;
        }
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gameState) {
        GameEngine.gameState = gameState;
    }
}
