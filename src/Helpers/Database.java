package Helpers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import objects.Score;

public class Database {
    private String databaseUrl = "jdbc:sqlite:database/score.db";
    private ConnectionSource connectionSource = null;
    private Dao<Score, Integer> scoreDao;

    public Database() {
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl);
            scoreDao = DaoManager.createDao(connectionSource, Score.class);
            TableUtils.createTableIfNotExists(connectionSource, Score.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData(Score score) {
        try {
            scoreDao.create(score);
            connectionSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
