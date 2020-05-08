package org.sct.easylib.api;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LovesAsuna
 * @date 2020/3/3 13:24
 */

public interface DataBaseManager {
    int store(String name, int id, String content) throws SQLException;
    int update(String name, int id, String content) throws SQLException;
    ResultSet read(String name) throws SQLException;
    boolean checkTable(String name);
    boolean createTable(String name, String sql);
}
