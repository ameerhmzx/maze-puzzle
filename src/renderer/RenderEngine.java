package renderer;

import enums.CellWall;
import interfaces.Constants;
import interfaces.GameActions;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import objects.Cell;
import objects.Player;
import objects.Puzzle;

import java.util.Map;


class RenderEngine implements Constants {
    private Puzzle puzzle;
    private GameActions gameActions;
    private Player player;

    RenderEngine(Puzzle puzzle, Player player, GameActions gameActions) {
        this.puzzle = puzzle;
        this.player = player;
        this.gameActions = gameActions;
        int height = this.puzzle.getBoard().getHeight();
        int width = this.puzzle.getBoard().getWidth();
//        this.puzzle.getBoard().getCell(0).setWall(CellWall.LEFT, false);
        this.puzzle.getBoard().getCell(height * width - 1).setWall(CellWall.RIGHT, false);
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
                SolveButton(),
                ShuffleButton(),
                sizeSelectComboBox(),
                rightSpacer,
                SettingButton()
        );
        toolBar.setMaxHeight(10);

        canvas.getChildren().add(player.getShape());

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

    private Button SolveButton() {
        Button btn = new Button("Solve");
        btn.getStyleClass().add("button");
        btn.setOnAction((value) -> gameActions.solve());
        return btn;
    }

    private Button ShuffleButton() {
        Button btn = new Button("Shuffle");
        btn.getStyleClass().add("button");
        btn.setOnAction((value) -> gameActions.shuffle());
        return btn;
    }

    private ComboBox sizeSelectComboBox() {
        ComboBox comboBox = new ComboBox<>(FXCollections.observableArrayList(SIZE_LIST));
        comboBox.setPromptText(puzzle.getBoard().getHeight() + " x " + puzzle.getBoard().getWidth());
        comboBox.setOnAction((value) -> {
            int size = SIZE_OF_GAME.get(comboBox.getSelectionModel().getSelectedItem().toString());
            gameActions.changeSize(size);
        });
        return comboBox;
    }

    private Button SettingButton() {
        Button btn = new Button();

        btn.getStyleClass().add("icon-button");
        btn.setPickOnBounds(true);

        return btn;
    }

}
