package core;

import Helpers.Constants;
import Helpers.Context;
import interfaces.OnButtonClick;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;
import objects.Puzzle;
import solutionStrategies.SolutionStrategy;

import java.util.Arrays;

public class MainFrameRenderer implements Constants {
    private ToolBar toolBar;
    private ToolBar statusBar;
    private SplitMenuButton solveButton, generateMazeButton;
    private CheckBox rectangularCB, animateCB;
    private ComboBox<Object> heightSelectBox, widthSelectBox, mazeTypeSelectBox;
    private Label scoreLabel;
    private Pane gameArea;

    private ToggleGroup solveMethodGroup = new ToggleGroup();
    private ToggleGroup generateMethodGroup = new ToggleGroup();

    private Context context;
    private OnButtonClick onButtonClick;

    public MainFrameRenderer(Context context, OnButtonClick onButtonClick) {
        this.context = context;
        this.onButtonClick = onButtonClick;

        initialize();
    }

    private void initialize() {
        //Independent Components
        solveButton = generateSolveButton();
        generateMazeButton = generateMazeButton();
        rectangularCB = generateRectangleCheckBox();
        animateCB = generateAnimateCheckBox();
        widthSelectBox = generateWidthSelectBox();
        heightSelectBox = generateHeightSelectBox();
        mazeTypeSelectBox = generateMazeTypeButton();

        //Dependent Components
        statusBar = generateStatusBar();
        toolBar = generateToolbar();
    }

    // Components

    private ToolBar generateToolbar() {
        final Pane leftSpacer = new Pane();
        HBox.setHgrow(leftSpacer, Priority.SOMETIMES);

        final Pane rightSpacer = new Pane();
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        ToolBar tb = new ToolBar(
                leftSpacer,
                solveButton,
                new Label("Size"),
                heightSelectBox,
                new Label(" x "),
                widthSelectBox,
                rectangularCB,
                animateCB,
                generateMazeButton,
                mazeTypeSelectBox,
                rightSpacer
        );
        tb.setMaxHeight(10);
        return tb;
    }

    private ToolBar generateStatusBar() {
        ToolBar sb = new ToolBar();
        sb.setPrefHeight(10);
        scoreLabel = new Label("Score");
        sb.getItems().add(scoreLabel);
        return sb;
    }

    private CheckBox generateRectangleCheckBox() {
        CheckBox rectangular = new CheckBox("Rectangular");
        if (context.getBoard() != null && (context.getBoard().getHeight() != context.getBoard().getWidth()))
            rectangular.setSelected(true);

        rectangular.setOnAction(e -> {
            widthSelectBox.setPromptText(heightSelectBox.getPromptText());
            widthSelectBox.getEditor().setText(heightSelectBox.getEditor().getText());
        });
        return rectangular;
    }

    private CheckBox generateAnimateCheckBox() {
        CheckBox animate = new CheckBox("Animate");
        if (context.animate) {
            animate.setSelected(true);
        }

        animate.setOnAction(e -> context.animate = !context.animate);
        return animate;
    }

    private SplitMenuButton generateMazeButton() {
        SplitMenuButton generateMaze = new SplitMenuButton();
        generateMaze.setText("Generate");

        for (LayoutStrategy layoutStrategy : LayoutStrategy.values()) {
            RadioMenuItem x = new RadioMenuItem(layoutStrategy.getName());
            x.setToggleGroup(generateMethodGroup);
            if (context.getPuzzle() != null && layoutStrategy.equals(context.getPuzzle().getLayoutStrategy()))
                x.setSelected(true);
            generateMaze.getItems().add(x);
        }

        generateMaze.setOnAction((value) -> {

            int width = 0, height = 0;

            if (context.getBoard() != null) {
                width = (widthSelectBox.getEditor().getText() == null ||
                        widthSelectBox.getEditor().getText().equals("")) ?
                        context.getBoard().getWidth() :
                        Integer.parseInt(widthSelectBox.getEditor().getText());

                height = (heightSelectBox.getEditor().getText() == null ||
                        heightSelectBox.getEditor().getText().equals("")) ?
                        context.getBoard().getHeight() :
                        Integer.parseInt(heightSelectBox.getEditor().getText());
            }

            LayoutStrategy layoutStrategy = Puzzle.DEFAULT_LAYOUT_STRATEGY;
            PostLayoutStrategy postLayoutStrategy = PostLayoutStrategy.getFromName(mazeTypeSelectBox.getValue().toString());

            for (MenuItem menuItem : generateMaze.getItems()) {
                if (((RadioMenuItem) menuItem).isSelected()) {
                    layoutStrategy = LayoutStrategy.getFromName(menuItem.getText());
                    break;
                }
            }

            onButtonClick.generate(width, height, layoutStrategy, postLayoutStrategy);
        });
        return generateMaze;
    }

    private SplitMenuButton generateSolveButton() {
        SplitMenuButton solve = new SplitMenuButton();
        solve.setText("Solve");
        for (SolutionStrategy solutionStrategy : SolutionStrategy.values()) {
            RadioMenuItem x = new RadioMenuItem(solutionStrategy.getName());
            x.setToggleGroup(solveMethodGroup);
            if (solutionStrategy.getName().equals(DEFAULT_SOLUTION_STRATEGY.getName()))
                x.setSelected(true);
            solve.getItems().add(x);
        }
        solve.setOnAction(value -> {
            for (MenuItem menuItem : solve.getItems()) {
                if (((RadioMenuItem) menuItem).isSelected()) {
                    onButtonClick.solve(SolutionStrategy.getFromName(menuItem.getText()));
                    break;
                }
            }
        });
        return solve;
    }

    private ComboBox<Object> generateMazeTypeButton() {
        ComboBox<Object> mazeType = new ComboBox<>(
                FXCollections.observableArrayList(
                        Arrays.stream(PostLayoutStrategy.values())
                                .map(PostLayoutStrategy::getName)
                                .toArray()
                )
        );
        if (context.getPuzzle() != null)
            mazeType.setValue(context.getPuzzle().getPostLayoutStrategies().getName());
        return mazeType;
    }

    private ComboBox<Object> generateWidthSelectBox() {
        ComboBox<Object> widthSelect = new ComboBox<>(FXCollections.observableArrayList(SIZE_LIST));
        widthSelect.setPrefWidth(70);
        widthSelect.setEditable(true);
        if (context.getBoard() != null)
            widthSelect.getEditor().setText(context.getBoard().getWidth() + "");
        widthSelect.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!rectangularCB.isSelected())
                heightSelectBox.getEditor().setText(newValue);
        });
        return widthSelect;
    }

    private ComboBox<Object> generateHeightSelectBox() {
        ComboBox<Object> heightSelect = new ComboBox<>(FXCollections.observableArrayList(SIZE_LIST));
        heightSelect.setPrefWidth(70);
        heightSelect.setEditable(true);
        if (context.getBoard() != null)
            heightSelect.getEditor().setText(context.getBoard().getHeight() + "");
        heightSelect.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!rectangularCB.isSelected())
                widthSelectBox.getEditor().setText(newValue);
        });
        return heightSelect;
    }

    // Getters

    public Parent getMainFrame() {
        BorderPane root = new BorderPane();
        gameArea = new Pane();

        VBox vbox = new VBox();
        vbox.getChildren().add(gameArea);
        vbox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.getChildren().add(vbox);
        hBox.setAlignment(Pos.CENTER);

        root.setCenter(hBox);
        root.setTop(toolBar);
        root.setBottom(statusBar);
        return root;
    }

    // Update Methods

    public void update() {
        if (gameArea.getChildren().size() > 0)
            gameArea.getChildren().remove(gameArea.getChildren().get(0));
        gameArea.getChildren().add(context.getGameBoardRenderer().getGameBoard());

        heightSelectBox.getEditor().setText(context.getBoard().getHeight() + "");
        widthSelectBox.getEditor().setText(context.getBoard().getWidth() + "");
        rectangularCB.setSelected(context.getBoard().getHeight() != context.getBoard().getWidth());
        animateCB.setSelected(context.animate);
        mazeTypeSelectBox.setValue(context.getPuzzle().getPostLayoutStrategies().getName());
        updateScore(0);
        for (MenuItem item : generateMazeButton.getItems()) {
            if (item.getText().equals(context.getPuzzle().getLayoutStrategy().getName())) {
                ((RadioMenuItem) item).setSelected(true);
            }
        }
    }

    public void updateScore(long score) {
        scoreLabel.setText("Score : " + score);
    }
}
