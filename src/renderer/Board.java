package renderer;

import javafx.scene.layout.GridPane;


public class Board {

    private static Board ourInstance = null;

    private static GridPane table;

    public static Board getInstance(int side) {
        if(ourInstance == null)
            ourInstance = new Board(side);
        return ourInstance;
    }

    public static void destroyInstance(){ourInstance = null;}

    private Board(int side) {
        table = new GridPane();
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                table.add((new Block(false, false, Math.random()>0.5, Math.random()>0.5).getCell()), i, j);
            }
        }
    }

    public GridPane getTable(){
        return table;
    }


}
