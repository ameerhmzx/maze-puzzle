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
import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;
import objects.Cell;
import objects.Puzzle;
import solutionStrategies.SolutionStrategy;

import java.util.*;


public class RenderEngine implements Constants {
    private Context context;
    private OnButtonClick onButtonClick;
    private OnLayoutUpdate onLayoutUpdate;
    private GridPane grid;

    private ToolBar toolBar;
    private ToolBar statusBar;
    private SplitMenuButton solveButton, generateMazeButton;
    private CheckBox rectangularCB;
    private ComboBox heightSelectBox, widthSelectBox, mazeTypeSelectBox;
    private Label scoreLabel;


    RenderEngine(Context context, OnButtonClick onButtonClick, OnLayoutUpdate onLayoutUpdate) {
        this.context = context;
        this.onLayoutUpdate = onLayoutUpdate;
        this.onButtonClick = onButtonClick;
        int height = context.getBoard().getHeight();
        int width = context.getBoard().getWidth();
        context.getBoard().getCell(height * width - 1).setWall(Direction.RIGHT, false);
    }

    Parent getRoot() {
        BorderPane root = new BorderPane();
        StackPane stackPane = new StackPane();
        Pane canvas = new Pane();

//        grid = renderEmptyBoard();
        grid = renderWalledBoard();

        generateToolbar();
        generateStatusBar();

        canvas.getChildren().add(context.getPlayer().getShape());

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

    public void updateScore(long score) {
        scoreLabel.setText("Score : " + score);
    }

    public void renderBoard() {
        for (int y = 0; y < context.getBoard().getHeight(); y++) {
            for (int x = 0; x < context.getBoard().getWidth(); x++) {
                Cell cell = context.getBoard().getCell(y, x);
                updateCell(cell);
            }
        }
    }

    private GridPane renderWalledBoard() {
        GridPane grid = new GridPane();
        for (int y = 0; y < context.getBoard().getHeight(); y++) {
            for (int x = 0; x < context.getBoard().getWidth(); x++) {
                Cell cell = context.getBoard().getCell(y, x);
                grid.add(renderAllWalledCell(cell, x, y), x, y);
            }
        }
        return grid;
    }

//    private GridPane renderEmptyBoard() {
//        GridPane grid = new GridPane();
//
//        for (int y = 0; y < context.getBoard().getHeight(); y++) {
//            for (int x = 0; x < context.getBoard().getWidth(); x++) {
//                grid.add(renderEmptyCell(), x, y);
//            }
//        }
//        return grid;
//    }

    private Region renderCell(Cell cell) {
        int height = context.getBoard().getHeight();
        int width = context.getBoard().getWidth();

        int x = cell.getLocation().x;
        int y = cell.getLocation().y;

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
    private Region renderAllWalledCell(Cell cell, int x, int y) {
        Region box = new Region();
        box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);

        String style = String.format("-fx-border-color: %s;", BORDER_COLOR);

        style += "-fx-border-style: solid;";

        style += String.format("-fx-border-radius: %d %d %d %d;",
                cell.getIndex() == 0 ? 2 : 0,
                cell.getIndex() == context.getBoard().getWidth() - 1 ? 2 : 0,
                cell.getIndex() == context.getBoard().getWidth() * context.getBoard().getHeight() - 1 ? 2 : 0,
                cell.getIndex() == context.getBoard().getWidth() * context.getBoard().getHeight() - context.getBoard().getWidth() ? 2 : 0
        );

        style += String.format("-fx-border-width: %d %d %d %d;",
                y == 0 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                x == context.getBoard().getWidth() - 1 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
                y == context.getBoard().getHeight() - 1 ? BORDER_WIDTH * 2 : BORDER_WIDTH,
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

    private Region renderColoredCell(String color, Cell cell) {
        int height = context.getBoard().getHeight();
        int width = context.getBoard().getWidth();

        int x = cell.getLocation().x;
        int y = cell.getLocation().y;

        Map<Direction, Boolean> walls = cell.getWalls();
        Region box = new Region();
        box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);

        String style = String.format("-fx-border-color: %s;", BORDER_COLOR);

        style = String.format("-fx-background-color: %s;", color);

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
        generateMazeTypeButton();

        toolBar = new ToolBar(
                leftSpacer,
                solveButton,
                new Label("Size"),
                heightSelectBox,
                new Label(" x "),
                widthSelectBox,
                rectangularCB,
                generateMazeButton,
                mazeTypeSelectBox,
                rightSpacer
        );
        toolBar.setMaxHeight(10);
    }

    private void generateRectangleCheckBox() {
        rectangularCB = new CheckBox("Rectangular");
        if (context.getBoard().getHeight() != context.getBoard().getWidth())
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
            if (layoutStrategy.equals(context.getPuzzle().getLayoutStrategy()))
                x.setSelected(true);
            generateMazeButton.getItems().add(x);
        }

        generateMazeButton.setOnAction((value) -> {
            int width = (widthSelectBox.getEditor().getText() == null || widthSelectBox.getEditor().getText().equals("")) ? context.getBoard().getWidth() : Integer.parseInt(widthSelectBox.getEditor().getText());
            int height = (heightSelectBox.getEditor().getText() == null || heightSelectBox.getEditor().getText().equals("")) ? context.getBoard().getHeight() : Integer.parseInt(heightSelectBox.getEditor().getText());
            LayoutStrategy layoutStrategy = Puzzle.DEFAULT_LAYOUT_STRATEGY;
            PostLayoutStrategy postLayoutStrategy = PostLayoutStrategy.getFromName(mazeTypeSelectBox.getValue().toString());

            for (MenuItem menuItem : generateMazeButton.getItems()) {
                if (((RadioMenuItem) menuItem).isSelected()) {
                    layoutStrategy = LayoutStrategy.getFromName(menuItem.getText());
                    break;
                }
            }

            onButtonClick.generate(width, height, layoutStrategy, postLayoutStrategy);
        });
    }

    private void generateSolveButton() {
        solveButton = new SplitMenuButton();
        solveButton.setText("Solve");
        ToggleGroup solveMethodGroup = new ToggleGroup();
        for (SolutionStrategy solutionStrategy : SolutionStrategy.values()) {
            RadioMenuItem x = new RadioMenuItem(solutionStrategy.getName());
            x.setToggleGroup(solveMethodGroup);
            if (solutionStrategy.getName().equals(DEFAULT_SOLUTION_STRATEGY.getName()))
                x.setSelected(true);
            solveButton.getItems().add(x);
        }
    }

    private void generateMazeTypeButton() {
        mazeTypeSelectBox = new ComboBox(FXCollections.observableArrayList(Arrays.stream(PostLayoutStrategy.values()).map(PostLayoutStrategy::getName).toArray()));
        mazeTypeSelectBox.setValue(context.getPuzzle().getPostLayoutStrategies().getName());
    }

    private void generateWidthSelectBox() {
        widthSelectBox = new ComboBox(FXCollections.observableArrayList(SIZE_LIST));
        widthSelectBox.setPrefWidth(70);
        widthSelectBox.setEditable(true);
        widthSelectBox.getEditor().setText(context.getBoard().getWidth() + "");
        widthSelectBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!rectangularCB.isSelected())
                heightSelectBox.getEditor().setText(newValue);
        });
    }

    private void generateHeightSelectBox() {
        heightSelectBox = new ComboBox(FXCollections.observableArrayList(SIZE_LIST));
        heightSelectBox.setPrefWidth(70);
        heightSelectBox.setEditable(true);
        heightSelectBox.getEditor().setText(context.getBoard().getHeight() + "");
        heightSelectBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!rectangularCB.isSelected())
                widthSelectBox.getEditor().setText(newValue);
        });
    }

    private void updateCell(Cell cell) {
        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        onLayoutUpdate.updated(() -> {
            grid.getChildren().set(cell.getIndex(), renderEmptyCell());
            grid.add(renderCell(cell), x, y);
        });
    }

    private void updateCell(Region cellImage, Cell cell) {
        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        onLayoutUpdate.updated(() -> {
            grid.getChildren().set(cell.getIndex(), renderEmptyCell());
            grid.add(cellImage, x, y);
        });
    }

//    void animateRandom() {
//        onLayoutUpdate.updated(() -> new Thread(() -> {
//            for (int i = 0; i < context.getBoard().getHeight(); i++) {
//                for (int j = 0; j < context.getBoard().getWidth(); j++) {
//                    int finalJ = j;
//                    int finalI = i;
//                    new Thread(() -> {
//                        Cell cell = context.getBoard().getCell(finalI, finalJ);
//                        try {
//                            Thread.sleep((int) (Math.random() * MAX_RANDOM_MAZE_DRAW_ANIMATION_RATE));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        updateCell(cell);
//                    }).start();
//                }
//            }
//        }).start());
//    }

    //TODO:: add all animations
    void animateGeneration() {
        LayoutChanges layoutChanges = context.getPuzzle().getLayoutChanges();
        ArrayList<Dictionary> changes = layoutChanges.getLayoutChanges();
        ArrayList<Thread> threads = new ArrayList<>();

        Thread thread;

        for (Dictionary dictionary : changes) {
            switch ((LayoutChange) dictionary.get("type")) {
                case BOARD_CREATED:
                    grid = renderWalledBoard();
                    break;
                case MOVE:
                    // Remove wall in the given direction
                    thread = new Thread(() -> {
                        Cell currCell = (Cell) dictionary.get("currentCell");
                        Cell nextCell = context.getBoard().getNeighbourCell(currCell, (Direction) dictionary.get("direction"));

                        Cell tempCell1 = currCell.clone();
                        Cell tempCell2 = nextCell.clone();

                        onLayoutUpdate.updated(() -> {
                            new Thread(() -> {
                                updateCell(tempCell1);
                                updateCell(tempCell2);
                            }).start();
                        });

                        try {
                            Thread.sleep(MAZE_DRAW_ANIMATION_RATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    threads.add(thread);
                    break;
                case TOUCH_CELL:
                    // Changes BG color
                    thread = new Thread(() -> {
                        Cell currCell = (Cell) dictionary.get("cell");

                        Cell tempCell = currCell.clone();

                        onLayoutUpdate.updated(() -> {
                            new Thread(() -> {
                                updateCell(renderColoredCell(TOUCH_CELL_COLOR, tempCell), tempCell);
                            }).start();
                        });

                        try {
                            Thread.sleep(MAZE_DRAW_ANIMATION_RATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    threads.add(thread);
                    break;
                case UNTOUCH_CELL:
                    thread = new Thread(() -> {
                        Cell currCell = (Cell) dictionary.get("cell");

                        Cell tempCell = currCell.clone();

                        onLayoutUpdate.updated(() -> {
                            new Thread(() -> {
                                updateCell(renderColoredCell(DEFALT_CELL_COLOR, tempCell), tempCell);
                            }).start();
                        });

                        try {
                            Thread.sleep(MAZE_DRAW_ANIMATION_RATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    threads.add(thread);
                    // reset colour
                    break;
                case TOUCH_ALL:
                    // touch all cells given in Arr
                    for (Cell cell : (Collection<Cell>) dictionary.get("cells")) {
                        thread = new Thread(() -> {
                            Cell tempCell = cell.clone();

                            onLayoutUpdate.updated(() -> {
                                new Thread(() -> {
                                    updateCell(renderColoredCell(TOUCH_CELL_COLOR, tempCell), tempCell);
                                }).start();
                            });

                            try {
                                Thread.sleep(MAZE_DRAW_ANIMATION_RATE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        threads.add(thread);
                    }
                    break;
                case UNTOUCH_ALL:
                    for (Cell cell : (Collection<Cell>) dictionary.get("cells")) {
                        thread = new Thread(() -> {
                            Cell tempCell = cell.clone();

                            onLayoutUpdate.updated(() -> {
                                new Thread(() -> {
                                    updateCell(renderColoredCell(DEFALT_CELL_COLOR, tempCell), tempCell);
                                }).start();
                            });

                            try {
                                Thread.sleep(MAZE_DRAW_ANIMATION_RATE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        threads.add(thread);
                    }
                    // untouch all cells given in Arr
                    break;
                case SET_CURRENT_CELL:
                    thread = new Thread(() -> {
                        Cell currCell = (Cell) dictionary.get("cell");

                        Cell tempCell = currCell.clone();

                        onLayoutUpdate.updated(() -> {
                            new Thread(() -> {
                                updateCell(renderColoredCell(SELECT_CELL_COLOR, tempCell), tempCell);
                            }).start();
                        });

                        try {
                            Thread.sleep(MAZE_DRAW_ANIMATION_RATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    threads.add(thread);
                    // Change color of selected cell
                    break;
            }
        }

        threads.add(new Thread(() -> {
            //ensure board rendering
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onLayoutUpdate.updated(this::renderBoard);
        }));

        new Thread(() -> {
            for (Thread th : threads) {
                th.start();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
