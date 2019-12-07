package core;

import Helpers.Constants;
import Helpers.Context;
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
import layoutStrategies.PostLayoutStrategy;
import objects.Player;
import objects.Puzzle;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants, OnButtonClick, OnWon, OnLayoutUpdate {
    private Context context;
    private Stage primaryStage;
    private Scene scene;

    private EventHandler<KeyEvent> KbdEventsHandler = this::kbdEvents;
    private boolean maximized = DEFAULT_WINDOW_MAXIMIZED;

    public static void runGame() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.context = new Context();
        this.primaryStage = primaryStage;
        scene = new Scene(new Label("Loading..."));
        newGame();
    }

    private void newGame() {
        newGame(DEFAULT_MAZE_WIDTH, DEFAULT_MAZE_HEIGHT, DEFAULT_LAYOUT_STRATEGY, DEFAULT_POST_LAYOUT_STRATEGY);
    }

    private void newGame(int width, int height, LayoutStrategy layoutStrategy, PostLayoutStrategy postLayoutStrategy) {
        context.setPuzzle(new Puzzle(width, height, layoutStrategy, postLayoutStrategy));
        context.setPlayer(new Player(context.getBoard(), this));

        context.setRenderEngine(new RenderEngine(context, this, this));

        scene.setRoot(context.getRenderEngine().getRoot());
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        adjustStageSize(maximized);

        scene.getStylesheets().add(getClass().getResource("../styles/style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        primaryStage.maximizedProperty().addListener((ov, t, t1) -> {
            maximized = t1;
            adjustStageSize(maximized);
        });

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(maximized);
        primaryStage.show();
        context.getRenderEngine().animateRandom();
    }

    private void kbdEvents(KeyEvent ke) {
        KeyCode kc = ke.getCode();
        if ((kc == UP || kc == DOWN || kc == LEFT || kc == RIGHT) && context.getGameState() == GameState.PLAYING) {
            context.getPlayer().move(kc);
            context.getRenderEngine().updateScore(context.getPlayer().getScore());
            ke.consume();
        } else {
            if (kc == R) {
                context.getPlayer().reset();
                context.setGameState(GameState.PLAYING);
                ke.consume();
            }
        }
    }

    private void adjustStageSize(boolean maximized) {
        if (!maximized) {
            primaryStage.setWidth((context.getPuzzle().getWidth() + MAZE_PADDING) * PIXEL_SIZE);
            primaryStage.setHeight((context.getPuzzle().getHeight() + MAZE_PADDING) * PIXEL_SIZE);
        }
    }

    @Override
    public void solve() {
        // TODO:: SOLVE
    }

    @Override
    public void generate(int width, int height, LayoutStrategy layoutStrategy, PostLayoutStrategy postLayoutStrategy) {
        newGame(width, height, layoutStrategy, postLayoutStrategy);
    }

    @Override
    public void gameWon() {
        context.setGameState(GameState.WON);
        System.out.println("WON");
        System.out.println("SCORE : " + context.getPlayer().getScore());
    }

    @Override
    public void updated(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
