package renderer;

import enums.CellWall;
import interfaces.Constants;
import interfaces.GameControls;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import objects.Cell;
import objects.Puzzle;

import java.util.Map;


class RenderEngine implements Constants {
    private Puzzle puzzle;
    private GameControls gameControls;

    RenderEngine(Puzzle puzzle, GameControls gameControls) {
        this.puzzle = puzzle;
        this.gameControls = gameControls;
        int size = this.puzzle.getSize();
//        this.puzzle.getBoard().getCell(0).setWall(CellWall.LEFT, false);
        this.puzzle.getBoard().getCell(size * size - 1).setWall(CellWall.RIGHT, false);
    }

    Parent getRoot() {
        BorderPane root = new BorderPane();
        StackPane stackPane = new StackPane();
        GridPane grid = renderBoard();
        Pane canvas = new Pane();

        final Pane leftSpacer = new Pane();
        HBox.setHgrow(leftSpacer, Priority.SOMETIMES);

        final Pane rightSpacer = new Pane();
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        ToolBar toolBar = new ToolBar(
                leftSpacer,
                new Button("SOLVE"),
                ShuffleButton(),
                sizeSelectComboBox(),
                rightSpacer,
                SettingButton()
        );
        toolBar.setMaxHeight(10);

        canvas.getChildren().add(puzzle.getPlayer().getShape());

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(grid, canvas);

        VBox vbox = new VBox();
        vbox.getChildren().add(stackPane);
        vbox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.getChildren().add(vbox);
        hBox.setAlignment(Pos.CENTER);

        root.setCenter(hBox);
        root.setTop(toolBar);
        return root;
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

    private Button SolveButton(){
        Button btn = new Button("SOLVE");
        btn.setOnAction((value)->gameControls.solve());
        return btn;
    }

    private Button ShuffleButton(){
        Button btn = new Button("SHUFFLE");
        btn.setOnAction((value)->gameControls.shuffle());
        return btn;
    }

    private ComboBox sizeSelectComboBox(){
        ComboBox comboBox = new ComboBox<>(FXCollections.observableArrayList(SIZE_LIST));
        comboBox.setPromptText(puzzle.getSize() + " x " + puzzle.getSize());
        comboBox.setOnAction((value)->{
            int size = SIZE_OF_GAME.get(comboBox.getSelectionModel().getSelectedItem().toString());
            gameControls.changeSize(size);
        });
        return comboBox;
    }

    private Button SettingButton(){
        Button btn = new Button();

        btn.getStyleClass().add("icon-button");
        btn.setPickOnBounds(true);

        return btn;
    }

}
