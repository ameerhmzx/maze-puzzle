package core;

import Helpers.Constants;
import enums.GameState;
import interfaces.OnButtonClick;
import interfaces.OnLayoutUpdate;
import interfaces.OnWon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import objects.Player;
import objects.Puzzle;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants, OnButtonClick, OnWon, OnLayoutUpdate {
    private static GameState gameState;
    private Puzzle puzzle;
    private Stage primaryStage;
    private Scene scene;
    private Player player;

    private EventHandler<KeyEvent> KbdEventsHandler = this::kbdEvents;
    private boolean maximized = DEFAULT_WINDOW_MAXIMIZED;

    public static void runGame() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        scene = new Scene(new Label("Loading..."));
        newGame();
    }

    private void newGame() {
        newGame(DEFAULT_MAZE_WIDTH, DEFAULT_MAZE_HEIGHT, DEFAULT_LAYOUT_STRATEGY);
    }

    private void newGame(int width, int height, LayoutStrategy layoutStrategy) {
        puzzle = new Puzzle(width, height, layoutStrategy);
        player = new Player(puzzle.getBoard(), this);
        gameState = GameState.PLAYING;

        RenderEngine renderEngine = new RenderEngine(puzzle, player, this, this);

        scene.setRoot(renderEngine.getRoot());
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        adjustStageSize(maximized);

        scene.getStylesheets().add(getClass().getResource("../styling/style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        primaryStage.maximizedProperty().addListener((ov, t, t1) -> {
            maximized = t1;
            adjustStageSize(maximized);
        });

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(maximized);
        primaryStage.show();
        renderEngine.animateRandom();
    }

    private void kbdEvents(KeyEvent ke) {
        KeyCode kc = ke.getCode();
        if ((kc == UP || kc == DOWN || kc == LEFT || kc == RIGHT) && gameState == GameState.PLAYING) {
            player.move(kc);
            ke.consume();
        } else {
            if (kc == R) {
                player.reset();
                gameState = GameState.PLAYING;
                ke.consume();
            }
        }
    }

    private void adjustStageSize(boolean maximized) {
        if (!maximized) {
            primaryStage.setWidth((puzzle.getBoard().getWidth() + MAZE_PADDING) * PIXEL_SIZE);
            primaryStage.setHeight((puzzle.getBoard().getHeight() + MAZE_PADDING) * PIXEL_SIZE);
        }
    }

    @Override
    public void solve() {
        // TODO:: SOLVE
    }

    @Override
    public void generate(int width, int height, LayoutStrategy layoutStrategy) {
        newGame(width, height, layoutStrategy);
    }

    @Override
    public void gameWon() {
        gameState = GameState.WON;
        System.out.println("WON");
        System.out.println("SCORE : " + player.getScore());
    }

    @Override
    public void updated(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
