package renderer;

import com.sun.org.apache.regexp.internal.REDebugCompiler;
import enums.Direction;
import interfaces.Constants;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import objects.Player;

public class RenderEngine implements Constants {

    private BorderPane borderPane;
    private StackPane stackPane;
    private GridPane grid;
    private Pane canvas;


    public Parent getRoot(){
        borderPane = new BorderPane();
        stackPane = new StackPane();
//        grid = new GridPane();
        grid = renderer.Board.getInstance(20).getTable();
        canvas = new Pane();

        canvas.getChildren().add(Player.getPlayer());

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(grid, canvas);
        borderPane.setCenter(stackPane);
        return borderPane;
    }

}
