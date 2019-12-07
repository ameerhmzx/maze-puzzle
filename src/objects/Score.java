package objects;

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

}
