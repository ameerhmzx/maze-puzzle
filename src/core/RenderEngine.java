package core;

import Helpers.Constants;
import Helpers.Context;
import enums.Direction;
import interfaces.OnButtonClick;
import interfaces.OnLayoutUpdate;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import layoutStrategies.LayoutStrategy;
import objects.Cell;
import objects.Player;
import objects.Puzzle;

import java.util.Map;


public class RenderEngine implements Constants {
    private Puzzle puzzle;
    private OnButtonClick onButtonClick;
    private Player player;
    private OnLayoutUpdate onLayoutUpdate;
    private GridPane grid;

    private ToolBar toolBar;
    private ToolBar statusBar;
    private SplitMenuButton solveButton, generateMazeButton;
    private CheckBox rectangularCB;
    private ComboBox heightSelectBox, widthSelectBox;
    private Label scoreLabel;


    RenderEngine(Context context, OnButtonClick onButtonClick, OnLayoutUpdate onLayoutUpdate) {
        this(context.getPuzzle(), context.getPlayer(), onButtonClick, onLayoutUpdate);
    }

    RenderEngine(Puzzle puzzle, Player player, OnButtonClick onButtonClick, OnLayoutUpdate onLayoutUpdate) {
        this.puzzle = puzzle;
        this.player = player;
        this.onLayoutUpdate = onLayoutUpdate;
        this.onButtonClick = onButtonClick;
        int height = this.puzzle.getBoard().getHeight();
        int width = this.puzzle.getBoard().getWidth();
        this.puzzle.getBoard().getCell(height * width - 1).setWall(Direction.RIGHT, false);
    }

    Parent getRoot() {
        BorderPane root = new BorderPane();
        StackPane stackPane = new StackPane();
        Pane canvas = new Pane();

        grid = renderEmptyBoard();

        generateToolbar();
        generateStatusBar();

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
        root.setBottom(statusBar);
        return root;
    }

    private void generateStatusBar() {
        statusBar = new ToolBar();
        statusBar.setPrefHeight(10);
        scoreLabel = new Label("Score");
        statusBar.getItems().add(scoreLabel);
    }

    public void updateScore(long score){
        scoreLabel.setText("Score : " + score);
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
        Map<Direction, Boolean> walls = cell.getWalls();
        Region box = new Region();
        box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);

        String style = String.format("-fx-border-color: %s;", BORDER_COLOR);

        style += String.format("-fx-border-style: %s %s %s %s;",
                walls.get(Direction.UP) ? "solid" : "hidden",
                walls.get(Direction.RIGHT) ? "solid" : "hidden",
                walls.get(Direction.DOWN) ? "solid" : "hidden",
                walls.get(Direction.LEFT) ? "solid" : "hidden"
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

    private void generateToolbar() {

        final Pane leftSpacer = new Pane();
        HBox.setHgrow(leftSpacer, Priority.SOMETIMES);

        final Pane rightSpacer = new Pane();
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        generateSolveButton();
        generateMazeButton();

        generateRectangleCheckBox();

        generateWidthSelectBox();
        generateHeightSelectBox();

        toolBar = new ToolBar(
                leftSpacer,
                solveButton,
                new Label("Size"),
                heightSelectBox,
                new Label(" x "),
                widthSelectBox,
                rectangularCB,
                generateMazeButton,
                rightSpacer
        );
        toolBar.setMaxHeight(10);
    }

    private void generateRectangleCheckBox() {
        rectangularCB = new CheckBox("Rectangular");
        if (puzzle.getHeight() != puzzle.getWidth())
            rectangularCB.setSelected(true);

        rectangularCB.setOnAction(e -> {
            widthSelectBox.setPromptText(heightSelectBox.getPromptText());
            widthSelectBox.getEditor().setText(heightSelectBox.getEditor().getText());
        });
    }

    private void generateMazeButton() {
        generateMazeButton = new SplitMenuButton();
        generateMazeButton.setText("Generate");

        ToggleGroup generateMethodGroup = new ToggleGroup();
        for (LayoutStrategy layoutStrategy : LayoutStrategy.values()) {
            RadioMenuItem x = new RadioMenuItem(layoutStrategy.getName());
            x.setToggleGroup(generateMethodGroup);
            if (layoutStrategy.equals(puzzle.getLayoutStrategy()))
                x.setSelected(true);
            generateMazeButton.getItems().add(x);
        }

        generateMazeButton.setOnAction((value) -> {
            int width = (widthSelectBox.getEditor().getText()==null || widthSelectBox.getEditor().getText().equals(""))?puzzle.getWidth():Integer.parseInt(widthSelectBox.getEditor().getText());
            int height = (heightSelectBox.getEditor().getText()==null || heightSelectBox.getEditor().getText().equals(""))?puzzle.getHeight():Integer.parseInt(heightSelectBox.getEditor().getText());
            LayoutStrategy layoutStrategy = Puzzle.DEFAULT_LAYOUT_STRATEGY;

            for(MenuItem menuItem : generateMazeButton.getItems()){
                if(((RadioMenuItem) menuItem).isSelected()) {
                    for(LayoutStrategy layoutStrategy1 : LayoutStrategy.values()){
                        if(layoutStrategy1.getName().equals(menuItem.getText())){
                            layoutStrategy = layoutStrategy1;
                        }
                    }
                    break;
                }
            }

            onButtonClick.generate(width, height, layoutStrategy);
        });
    }

    private void generateSolveButton() {
        solveButton = new SplitMenuButton();
        solveButton.setText("Solve");
        ToggleGroup solveMethodGroup = new ToggleGroup();
        RadioMenuItem btn1 = new RadioMenuItem("Method 1");
        RadioMenuItem btn2 = new RadioMenuItem("Method 2");
        btn1.setToggleGroup(solveMethodGroup);
        btn2.setToggleGroup(solveMethodGroup);

        solveButton.getItems().addAll(btn1, btn2);
    }

    private void generateWidthSelectBox() {
        widthSelectBox = new ComboBox(FXCollections.observableArrayList(SIZE_LIST));
        widthSelectBox.setPrefWidth(70);
        widthSelectBox.setEditable(true);
        widthSelectBox.getEditor().setText(puzzle.getBoard().getWidth() + "");
        widthSelectBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!rectangularCB.isSelected())
                heightSelectBox.getEditor().setText(newValue);
        });
    }

    private void generateHeightSelectBox() {
        heightSelectBox = new ComboBox(FXCollections.observableArrayList(SIZE_LIST));
        heightSelectBox.setPrefWidth(70);
        heightSelectBox.setEditable(true);
        heightSelectBox.getEditor().setText(puzzle.getBoard().getHeight() + "");
        heightSelectBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!rectangularCB.isSelected())
                widthSelectBox.getEditor().setText(newValue);
        });
    }

    private void updateCell(Cell cell, int x, int y) {
        onLayoutUpdate.updated(() -> {
            //noinspection SuspiciousNameCombination
            grid.add(renderCell(cell, y, x, puzzle.getWidth(), puzzle.getHeight()), y, x);
        });
    }

    void animateRandom() {
        onLayoutUpdate.updated(() -> new Thread(() -> {
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
        }).start());
    }

}
