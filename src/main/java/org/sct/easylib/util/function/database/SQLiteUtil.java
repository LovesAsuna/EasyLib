package org.sct.easylib.util.function.database;

import org.sct.easylib.api.DataBaseManager;
import org.sct.easylib.util.function.stack.StackTrace;

import java.sql.*;

/**
 * @author LovesAsuna
 * @date 2020/3/3 14:25
 */

public class SQLiteUtil implements DataBaseManager {
    private Connection connection;
    private Statement statement;
    private String url;


    public SQLiteUtil(String url) {
        try {
            this.url = url;
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            StackTrace.printStackTrace(e);
        }
    }

    @Override
    public int store(String name, int id, String content) throws SQLException {
        return statement.executeUpdate("INSERT INTO " + name + " VALUES ('" + id + "','" + content + "')");
    }

    @Override
    public int update(String name, int id, String content) throws SQLException {
        try {
            return statement.executeUpdate("update " + name + " set ITEM='" + content + "' where id='" + id + "'");
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public ResultSet read(String name) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from " + name);
        return resultSet;
    }

    @Override
    public boolean checkTable(String name) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='" + name + "'");
            resultSet.getString(1);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean createTable(String name, String sql) {
        if (!checkTable(name)) {
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                return false;
            }
            return true;
        }
        return false;
    }
}
