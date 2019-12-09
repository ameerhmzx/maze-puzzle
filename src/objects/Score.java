package objects;

import Helpers.Context;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "score")
public class Score {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String playerName;

    @DatabaseField(canBeNull = false)
    private long score;

    @DatabaseField
    private String layoutStrategy;

    @DatabaseField
    private String postLayoutStrategy;

    @DatabaseField
    private int width;

    @DatabaseField
    private int height;

    @DatabaseField
    private int minimumMovesRequired;

    @DatabaseField
    private int movesUsed;

    public Score() {
    }

    public Score(Context context) {
        this.playerName = context.getPlayerName();
        this.score = context.getPlayer().getScore();
        this.layoutStrategy = context.getPuzzle().getLayoutStrategy().getName();
        this.postLayoutStrategy = context.getPuzzle().getPostLayoutStrategies().getName();
        this.height = context.getPuzzle().getHeight();
        this.width = context.getPuzzle().getWidth();
        this.movesUsed = context.getNumberOfMoves();
        this.minimumMovesRequired = context.getMinimumMovesRequired();
    }
}
