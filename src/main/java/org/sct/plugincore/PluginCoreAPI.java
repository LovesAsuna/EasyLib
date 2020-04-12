package org.sct.plugincore;

import lombok.Getter;
import org.sct.plugincore.util.function.database.DataBaseManager;
import org.sct.plugincore.util.function.database.MysqlUtil;
import org.sct.plugincore.util.function.database.SQLiteUtil;
import org.sct.plugincore.util.plugin.GitHubAPI;
import org.sct.plugincore.util.reflectutil.Reflections;

/**
 * @author LovesAsuna
 * @date 2020/3/23 20:53
 */

public class PluginCoreAPI {

    @Getter
    private Reflections reflections;
    @Getter
    private GitHubAPI gitHubAPI;

    protected PluginCoreAPI() {
        reflections = new Reflections();
        gitHubAPI = new GitHubAPI();
    }

    public DataBaseManager getDataBaseManager(dataBaseType type) {
        if (type == dataBaseType.MYSQL) {
            return new MysqlUtil();
        } else if (type == dataBaseType.SQLITE) {
            return new SQLiteUtil();
        } else {
            return null;
        }
    }

    public enum dataBaseType {
        MYSQL,
        SQLITE
    }

}
