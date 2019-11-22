package renderer;

import enums.Direction;
import interfaces.Constants;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import objects.Puzzle;

public class Init extends Application implements Constants {

    public static void main(String[] args) {
        Init.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        puzzle = new Puzzle(20, LayoutStrategy.PRIM_RANDOMIZATION);


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
