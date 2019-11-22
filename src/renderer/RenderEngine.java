package renderer;

import enums.CellWall;
import interfaces.Constants;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import objects.Cell;
import objects.Player;
import objects.Puzzle;

import java.util.ArrayList;

public class RenderEngine implements Constants {

    private BorderPane borderPane;
    private StackPane stackPane;
    private GridPane grid;
    private Pane canvas;

    private Puzzle puzzle;

    public RenderEngine(Puzzle puzzle) {
        this.puzzle = puzzle;
    }


    public Parent getRoot(){
        borderPane = new BorderPane();
        stackPane = new StackPane();
        grid = renderBoard();
//        grid = renderer.Board.getInstance(20).getTable();
        canvas = new Pane();

        canvas.getChildren().add(Player.getPlayer());

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(grid, canvas);
        borderPane.setCenter(stackPane);
        return borderPane;
    }

    private GridPane renderBoard() {
        ArrayList<Cell> cells = puzzle.getBoard().getCells();
        grid = new GridPane();
        for (int i = 0; i < puzzle.getBoard().getHeight(); i++) {
            for (int j = 0; j < puzzle.getBoard().getWidth(); j++) {
//                grid.add((new Block(false, false, Math.random()>0.5, Math.random()>0.5).getCell()), i, j);
                Cell cell = cells.get(j+(i*puzzle.getBoard().getWidth()));
                grid.add((new Block(cell.hasWall(CellWall.TOP), cell.hasWall(CellWall.RIGHT),cell.hasWall(CellWall.BOTTOM),cell.hasWall(CellWall.LEFT))).getBox(), i ,j);
            }
        }
        return grid;
    }

}
