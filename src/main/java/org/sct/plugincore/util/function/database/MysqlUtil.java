package org.sct.plugincore.util.function.database;

import org.sct.plugincore.api.DataBaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LovesAsuna
 * @date 2020/3/3 13:27
 */

public class MysqlUtil implements DataBaseManager {

    public MysqlUtil() {
    }

    @Override
    public int store(String name, int id, String content) throws SQLException {
        return 0;
    }

    @Override
    public int update(String name, int id, String content) throws SQLException {
        return 0;
    }

    @Override
    public ResultSet read(String name) throws SQLException {
        return null;
    }

    @Override
    public boolean checkTable(String name) {
        return false;
    }

    @Override
    public boolean createTable(String name, String sql) {
        return false;
    }
}
