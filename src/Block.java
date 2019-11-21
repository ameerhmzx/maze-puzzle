import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;

public class Block {
    private boolean top, right, bottom, left;
    Region box;

    public Block() {
        this(true, true, true, true);
    }

    public Block(boolean top, boolean right, boolean bottom, boolean left) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;

        box = new Region();
        box.setMinSize(25, 25);
        box.resize(25, 25);
        box.setBorder(
                new Border(
                        new BorderStroke(
                                Color.valueOf("000000"),
                                Color.valueOf("000000"),
                                Color.valueOf("000000"),
                                Color.valueOf("000000"),
                                top?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                right?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                bottom?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                left?BorderStrokeStyle.SOLID:BorderStrokeStyle.NONE,
                                CornerRadii.EMPTY,
                                BorderWidths.DEFAULT,
                                null
                                )));
    }

    public Region getCell(){
        return box;
    }
}
