package core;

import Helpers.Constants;
import Helpers.Context;
import enums.Direction;
import enums.GameState;
import interfaces.OnLayoutUpdate;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import layoutChanges.LayoutChange;
import layoutChanges.LayoutChanges;
import objects.Cell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;


public class GameBoardRenderer implements Constants {
    private Context context;
    private OnLayoutUpdate onLayoutUpdate;
    private GridPane grid;

    GameBoardRenderer(Context context, OnLayoutUpdate onLayoutUpdate) {
        this.context = context;
        this.onLayoutUpdate = onLayoutUpdate;
        int height = context.getBoard().getHeight();
        int width = context.getBoard().getWidth();
        context.getBoard().getCell(height * width - 1).setWall(Direction.RIGHT, false);
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
                grid.add(renderCell(cell, true), x, y);
            }
        }
        return grid;
    }

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

    private Region renderCell(Cell cell, Boolean allWalled) {
        if (allWalled) {
            int x = cell.getLocation().x;
            int y = cell.getLocation().y;

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
        } else {
            Region box = new Region();
            box.setMinSize(PIXEL_SIZE, PIXEL_SIZE);
            return box;
        }
    }

    private Region renderCell(Cell cell, String color) {
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

    private void updateCell(Cell cell) {
        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        onLayoutUpdate.updated(() -> {
            grid.getChildren().set(cell.getIndex(), renderCell(null, false));
            grid.add(renderCell(cell), x, y);
        });
    }
    private void updateCell(Region cellImage, Cell cell) {
        int x = cell.getLocation().x;
        int y = cell.getLocation().y;
        onLayoutUpdate.updated(() -> {
            grid.getChildren().set(cell.getIndex(), renderCell(null, false));
            grid.add(cellImage, x, y);
        });
    }

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
                                updateCell(renderCell(tempCell, TOUCH_CELL_COLOR), tempCell);
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
                                updateCell(renderCell(tempCell, DEFALT_CELL_COLOR), tempCell);
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
                                    updateCell(renderCell(tempCell, TOUCH_CELL_COLOR), tempCell);
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
                                    updateCell(renderCell(tempCell, DEFALT_CELL_COLOR), tempCell);
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
                                updateCell(renderCell(tempCell, SELECT_CELL_COLOR), tempCell);
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
            context.setGameState(GameState.PLAYING);
        }).start();

    }

    public Node getGameBoard() {
        StackPane stackPane = new StackPane();
        Pane canvas = new Pane();

        grid = renderWalledBoard();

        canvas.getChildren().add(context.getPlayer().getShape());

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(grid, canvas);
        return stackPane;
    }
}
