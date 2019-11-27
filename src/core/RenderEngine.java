package core;

import Helpers.Constants;
import enums.CellWall;
import interfaces.OnButtonClick;
import interfaces.OnLayoutUpdate;
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
    private OnButtonClick onButtonClick;
    private Player player;
    private OnLayoutUpdate onLayoutUpdate;
    private GridPane grid;

    RenderEngine(Puzzle puzzle, Player player, OnButtonClick onButtonClick, OnLayoutUpdate onLayoutUpdate) {
        this.puzzle = puzzle;
        this.player = player;
        this.onLayoutUpdate = onLayoutUpdate;
        this.onButtonClick = onButtonClick;
        int height = this.puzzle.getBoard().getHeight();
        int width = this.puzzle.getBoard().getWidth();
        this.puzzle.getBoard().getCell(height * width - 1).setWall(CellWall.RIGHT, false);
    }

    Parent getRoot() {
        BorderPane root = new BorderPane();
        StackPane stackPane = new StackPane();
        Pane canvas = new Pane();

        grid = renderEmptyBoard();

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
                grid.add(renderCell(cell, x, y, puzzle.getWidth(), puzzle.getHeight()), x, y);
            }
        }
        return grid;
    }

    private GridPane renderEmptyBoard() {
        GridPane grid = new GridPane();

        for (int y = 0; y < puzzle.getBoard().getHeight(); y++) {
            for (int x = 0; x < puzzle.getBoard().getWidth(); x++) {
                grid.add(renderEmptyCell(), x, y);
            }
        }
        return grid;
    }

    private Region renderCell(Cell cell, int x, int y, int width, int height) {
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
                cell.getIndex() == width - 1 ? 2 : 0,
                cell.getIndex() == width * height - 1 ? 2 : 0,
                cell.getIndex() == width * height - width ? 2 : 0
        );

        style += String.format("-fx-border-width: %d %d %d %d;",
                y == 0 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                x == width - 1 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                y == height - 1 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                x == 0 ? BORDER_WIDTH * 2 : BORDER_WIDTH
        );

        box.setStyle(style);
        return box;
    }

    private Region renderEmptyCell() {
        Region box = new Region();
        box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);
        return box;
    }

    private Button SolveButton() {
        Button btn = new Button("Solve");
        btn.getStyleClass().add("button");
        btn.setOnAction((value) -> onButtonClick.solve());
        return btn;
    }

    private Button ShuffleButton() {
        Button btn = new Button("Shuffle");
        btn.getStyleClass().add("button");
        btn.setOnAction((value) -> onButtonClick.shuffle());
        return btn;
    }

    private ComboBox sizeSelectComboBox() {
        ComboBox comboBox = new ComboBox<>(FXCollections.observableArrayList(SIZE_LIST));
        comboBox.setPromptText(puzzle.getBoard().getHeight() + " x " + puzzle.getBoard().getWidth());
        comboBox.setOnAction((value) -> {
            int size = SIZE_OF_GAME.get(comboBox.getSelectionModel().getSelectedItem().toString());
            onButtonClick.changeSize(size, size);
        });
        return comboBox;
    }

    private Button SettingButton() {
        Button btn = new Button();

        btn.getStyleClass().add("icon-button");
        btn.setPickOnBounds(true);

        return btn;
    }

    public void updateCell(Cell cell, int x, int y) {
        onLayoutUpdate.updated(() -> {
            //noinspection SuspiciousNameCombination
            grid.add(renderCell(cell, y, x, puzzle.getWidth(), puzzle.getHeight()), y, x);
        });
    }

    public void animateRandom() {
        onLayoutUpdate.updated(() -> {
            new Thread(() -> {
                for (int i = 0; i < puzzle.getBoard().getHeight(); i++) {
                    for (int j = 0; j < puzzle.getBoard().getWidth(); j++) {
                        int finalJ = j;
                        int finalI = i;
                        new Thread(() -> {
                            Cell cell = puzzle.getBoard().getCell(finalI, finalJ);
                            try {
                                Thread.sleep((int) (Math.random() * MAX_RANDOM_MAZE_DRAW_ANIMATION_RATE));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            updateCell(cell, finalI, finalJ);
                        }).start();
                    }
                }
            }).start();
        });
    }

}
