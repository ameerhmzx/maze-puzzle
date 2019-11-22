package renderer;

import enums.Direction;
import enums.LayoutStrategy;
import interfaces.Constants;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import objects.Player;
import objects.Puzzle;

public class Init extends Application implements Constants {

    public static void main(String[] args) {
        Init.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Puzzle puzzle = new Puzzle(10, LayoutStrategy.RECURSIVE_BACK_TRACK);

        Parent root = (new RenderEngine(puzzle)).getRoot();
        Scene scene = new Scene(root, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::kbdEventsHandler);

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void kbdEventsHandler(KeyEvent ke){
        System.out.println("Key Pressed: " + ke.getCode());
        switch (ke.getCode()){
            case UP:
                Player.moveCharacter(Direction.UP);
                break;
            case RIGHT:
                Player.moveCharacter(Direction.RIGHT);
                break;
            case LEFT:
                Player.moveCharacter(Direction.LEFT);
                break;
            case DOWN:
                Player.moveCharacter(Direction.DOWN);
                break;
            case R:
                Player.reset();
                break;
        }
        ke.consume();
    }

}
