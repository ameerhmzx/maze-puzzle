package renderer;

import enums.CellWall;
import interfaces.Constants;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import objects.Cell;
import objects.Puzzle;


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
        canvas = new Pane();

        canvas.getChildren().add(puzzle.getPlayer().getShape());

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(grid, canvas);
        borderPane.setCenter(stackPane);
        return borderPane;
    }

    private GridPane renderBoard() {
        grid = new GridPane();
        for (int i = 0; i < puzzle.getBoard().getHeight(); i++) {
            for (int j = 0; j < puzzle.getBoard().getWidth(); j++) {
                Cell cell = puzzle.getBoard().getCell(j,i);
                grid.add(
                        renderCell(
                                cell.hasWall(CellWall.TOP),
                                (i != puzzle.getSize() - 1 || j != puzzle.getSize() - 1) && cell.hasWall(CellWall.RIGHT),
                                cell.hasWall(CellWall.BOTTOM),
                                (i != 0 || j != 0) && cell.hasWall(CellWall.LEFT)),
                        i ,j);
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
                                BORDER_COLOR,
                                BORDER_COLOR,
                                BORDER_COLOR,
                                BORDER_COLOR,
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
