package renderer;

import enums.GameState;
import interfaces.Constants;
import interfaces.GameActions;
import interfaces.WonSignal;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import objects.Player;
import objects.Puzzle;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants , GameActions, WonSignal {
    private Puzzle puzzle;
    private static GameState gameState;
    private Stage primaryStage;
    private Scene scene;
    private Player player;

    private EventHandler<KeyEvent> KbdEventsHandler = this::kbdEvents;
    private boolean maximized = DEFAULT_WINDOW_MAXIMIZED;

    public static void runGame() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        scene = new Scene(new Label("Loading..."));
        newGame(DEFAULT_MAZE_SIZE, LayoutStrategy.RECURSIVE_BACK_TRACK);
    }

    private void kbdEvents(KeyEvent ke){
        KeyCode kc = ke.getCode();
        if((kc == UP || kc == DOWN || kc == LEFT || kc == RIGHT) && gameState == GameState.PLAYING){
            player.move(kc);
        }else{
            switch (kc){
                case R:
                    player.reset();
                    gameState = GameState.PLAYING;
                    break;
            }
        }
        ke.consume();
    }

    private void newGame(int size, LayoutStrategy layoutStrategy){
        puzzle = new Puzzle(size, layoutStrategy);
        player = new Player(puzzle.getBoard(), this);
        gameState = GameState.PLAYING;

        Parent root = (new RenderEngine(puzzle, player,this)).getRoot();
        scene.setRoot(root);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED,KbdEventsHandler);
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

    @Override
    public void gameWon() {
        gameState = GameState.WON;
        System.out.println("WON");
    }
}
