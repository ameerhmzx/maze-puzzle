package renderer;

import enums.Direction;
import enums.GameState;
import interfaces.Constants;
import interfaces.GameControls;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import objects.Puzzle;

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;
import java.security.Key;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants , GameControls {
    private Puzzle puzzle;
    private static GameState gameState;
    private Stage primaryStage;
    private Scene scene;

    private boolean maximixed = DEFAULT_WINDOW_MAXIMIZED;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        scene = new Scene(new Label("Loading..."));
        newGame(DEFAULT_MAZE_SIZE, LayoutStrategy.RECURSIVE_BACK_TRACK);
    }

    private EventHandler<KeyEvent> KbdEventsHandler = this::kbdEvents;

    private void kbdEvents(KeyEvent ke){
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

    private void newGame(int size, LayoutStrategy layoutStrategy){
        puzzle = new Puzzle(size, layoutStrategy);
        gameState = GameState.PLAYING;

        Parent root = (new RenderEngine(puzzle, this)).getRoot();
        scene.setRoot(root);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED,KbdEventsHandler);
        adjustStageSize(maximixed);

        scene.getStylesheets().add(getClass().getResource("../styling/style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        primaryStage.maximizedProperty().addListener((ov, t, t1) -> {
            maximixed = t1;
            adjustStageSize(maximixed);
        });

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(maximixed);
        primaryStage.show();
    }

    private void adjustStageSize(boolean maximixed){
        if(!maximixed) {
            primaryStage.setWidth((puzzle.getSize() + MAZE_PADDING) * PIXEL_SIZE);
            primaryStage.setHeight((puzzle.getSize() + MAZE_PADDING) * PIXEL_SIZE);
        }
    }

    @Override
    public void shuffle() {
        newGame(puzzle.getSize(), puzzle.getLayoutStrategy());
    }

    @Override
    public void solve() {
        // TODO:: SOLVE
    }

    @Override
    public void changeSize(int size) {
        newGame(size, puzzle.getLayoutStrategy());
    }
}
