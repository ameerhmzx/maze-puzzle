package renderer;

import enums.CellWall;
import interfaces.Constants;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.ir.Block;
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
                grid.add(renderCell(cell.hasWall(CellWall.TOP), cell.hasWall(CellWall.RIGHT),cell.hasWall(CellWall.BOTTOM),cell.hasWall(CellWall.LEFT)), i ,j);
            }
        }
        return grid;
    }

    private Region renderCell(boolean top, boolean right, boolean bottom, boolean left){
        Region box = new Region();
        box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);
        box.setBorder(
                new Border(
                        new BorderStroke(
                                Color.valueOf("000000"),
                                Color.valueOf("000000"),
                                Color.valueOf("000000"),
                                Color.valueOf("000000"),
                                top?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                right?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                bottom?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                left?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                CornerRadii.EMPTY,
                                BorderWidths.DEFAULT,
                                null
                        )));
        return box;
    }


}
