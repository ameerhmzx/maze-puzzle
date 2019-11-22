package renderer;

import enums.CellWall;
import interfaces.Constants;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import objects.Cell;
import objects.Puzzle;

import java.util.Map;


class RenderEngine implements Constants {
    private Puzzle puzzle;

    RenderEngine(Puzzle puzzle) {
        this.puzzle = puzzle;
        int size = this.puzzle.getSize();
        this.puzzle.getBoard().getCell(0).setWall(CellWall.LEFT, false);
        this.puzzle.getBoard().getCell(size * size - 1).setWall(CellWall.RIGHT, false);
    }

    Parent getRoot() {
        StackPane stackPane = new StackPane();
        GridPane grid = renderBoard();
        Pane canvas = new Pane();

        canvas.getChildren().add(puzzle.getPlayer().getShape());

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(grid, canvas);

        VBox vbox = new VBox();
        vbox.getChildren().add(stackPane);
        vbox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.getChildren().add(vbox);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private GridPane renderBoard() {
        GridPane grid = new GridPane();

        for (int y = 0; y < puzzle.getBoard().getHeight(); y++) {
            for (int x = 0; x < puzzle.getBoard().getWidth(); x++) {
                Cell cell = puzzle.getBoard().getCell(y, x);
                grid.add(renderCell(cell, x, y, puzzle.getSize()), x, y);
            }
        }
        return grid;
    }

    private Region renderCell(Cell cell, int x, int y, int size) {
        Map<CellWall, Boolean> walls = cell.getWalls();
        Region box = new Region();
        box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);

        String style = String.format("-fx-border-color: %s;", BORDER_COLOR);

        style += String.format("-fx-border-style: %s %s %s %s;",
                walls.get(CellWall.TOP) ? "solid" : "hidden",
                walls.get(CellWall.RIGHT) ? "solid" : "hidden",
                walls.get(CellWall.BOTTOM) ? "solid" : "hidden",
                walls.get(CellWall.LEFT) ? "solid" : "hidden"
        );

        style += String.format("-fx-border-radius: %d %d %d %d;",
                cell.getIndex() == 0 ? 2 : 0,
                cell.getIndex() == size - 1 ? 2 : 0,
                cell.getIndex() == size * size - 1 ? 2 : 0,
                cell.getIndex() == size * size - size ? 2 : 0
        );

        style += String.format("-fx-border-width: %d %d %d %d;",
                y == 0 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                x == size - 1 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                y == size - 1 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                x == 0 ? BORDER_WIDTH * 2 : BORDER_WIDTH
        );

        box.setStyle(style);
        return box;
    }


}
