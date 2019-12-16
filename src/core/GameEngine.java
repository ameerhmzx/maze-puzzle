package core;

import Helpers.Constants;
import Helpers.Context;
import Helpers.Database;
import enums.Direction;
import enums.GameState;
import interfaces.OnButtonClick;
import interfaces.OnLayoutUpdate;
import interfaces.OnWon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;
import objects.Cell;
import objects.Player;
import objects.Puzzle;
import objects.Score;
import solutionStrategies.SolutionStrategy;

import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants, OnButtonClick, OnWon, OnLayoutUpdate {
    private Context context;
    private Stage primaryStage;
    private Scene scene;

//    private EventHandler<KeyEvent> KbdEventsHandler = this::kbdEvents;
    private boolean maximized = DEFAULT_WINDOW_MAXIMIZED;

    public static void runGame() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.context = new Context();
//        this.primaryStage = primaryStage;

        context.setMainFrameRenderer(new MainFrameRenderer(context, this));
        scene = new Scene(context.getMainFrameRenderer().getMainFrame());
        scene.getStylesheets().add(getClass().getResource("../styles/style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::kbdEvents);

        adjustStageSize(maximized);

        primaryStage.maximizedProperty().addListener((ov, t, t1) -> {
            maximized = t1;
            adjustStageSize(maximized);
        });

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(maximized);
        primaryStage.show();

        newGame();
    }

    private void newGame() {
        newGame(DEFAULT_MAZE_WIDTH, DEFAULT_MAZE_HEIGHT, DEFAULT_LAYOUT_STRATEGY, DEFAULT_POST_LAYOUT_STRATEGY);
    }

    private void newGame(int width, int height, LayoutStrategy layoutStrategy, PostLayoutStrategy postLayoutStrategy) {
        context.setPuzzle(new Puzzle(width, height, layoutStrategy, postLayoutStrategy));
        context.setPlayer(new Player(context, this));

        context.setGameBoardRenderer(new GameBoardRenderer(context, this));
        context.getMainFrameRenderer().update();

        if (context.animate) {
            context.getGameBoardRenderer().animateGeneration();
            context.setGameState(GameState.ANIMATING);
        } else {
            context.getGameBoardRenderer().renderBoard();
            context.setGameState(GameState.PLAYING);
        }
    }

    private void kbdEvents(KeyEvent ke) {
        KeyCode kc = ke.getCode();
        if ((kc == UP || kc == DOWN || kc == LEFT || kc == RIGHT) && context.getGameState() == GameState.PLAYING) {
            context.getPlayer().move(kc);
            context.getMainFrameRenderer().updateScore(context.getPlayer().getScore());
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
    public void solve(SolutionStrategy solutionStrategy) {
        int[] location = context.getPlayer().getLocation();
        Cell currentCell = context.getBoard().getCell(location[0], location[1]);
        ArrayList<Direction> moves = context.getPuzzle().solve(currentCell, solutionStrategy);

        ArrayList<Thread> threads = new ArrayList<>();

        for (Direction dir : moves) {
            threads.add(new Thread(()->{
                context.getPlayer().move(dir, false);
            }));
        }

        new Thread(()->{
            for (Thread thread: threads){
                thread.start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

        TextInputDialog dialog = new TextInputDialog("unnamed");
        dialog.setTitle("Enter Name");
        dialog.setHeaderText("Your score will be saved for calculating High score.\nScore: " + context.getPlayer().getScore());
        dialog.setContentText("Please enter your name:");

        Optional<String> result2 = dialog.showAndWait();
        result2.ifPresent(s2 -> {
            context.setPlayerName(s2);
            Score score = new Score(context);
            Database db = new Database();
            db.addData(score);
        });
    }

    @Override
    public void updated(Runnable runnable) {
        Platform.runLater(runnable);
    }
}