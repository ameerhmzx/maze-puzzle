package objects;

import Helpers.Context;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;

@DatabaseTable(tableName = "score")
public class Score {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String playerName;

    @DatabaseField(canBeNull = false)
    private long score;

    @DatabaseField
    private LayoutStrategy layoutStrategy;

    @DatabaseField
    private PostLayoutStrategy postLayoutStrategy;

    public Score(Context context) {
        this.playerName = context.getPlayerName();
        this.score = context.getPlayer().getScore();
        this.layoutStrategy = context.getPuzzle().getLayoutStrategy();
        this.postLayoutStrategy = context.getPuzzle().getPostLayoutStrategies();
    }
}
