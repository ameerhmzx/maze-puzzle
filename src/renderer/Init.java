package renderer;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Init extends Application {

    public static void main(String[] args) {
        Init.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = Board.getInstance(20).getTable();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
