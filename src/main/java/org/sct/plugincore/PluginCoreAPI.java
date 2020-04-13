package org.sct.plugincore;

import lombok.Getter;
import org.sct.plugincore.api.*;
import org.sct.plugincore.api.DataBaseManager;
import org.sct.plugincore.util.function.database.MysqlUtil;
import org.sct.plugincore.util.function.database.SQLiteUtil;
import org.sct.plugincore.util.function.econoomy.EcoUtil;
import org.sct.plugincore.util.function.item.MaterialUtil;
import org.sct.plugincore.util.function.player.LocationUtil;
import org.sct.plugincore.util.plugin.GitHub;
import org.sct.plugincore.util.reflectutil.Reflections;

/**
 * @author LovesAsuna
 * @date 2020/3/23 20:53
 */

public class PluginCoreAPI {

    @Getter
    private ReflectAPI reflectAPI;
    @Getter
    private GitHubAPI gitHubAPI;
    @Getter
    private MaterialAPI materialAPI;
    @Getter
    private LocationAPI locationAPI;
    @Getter
    private EcoAPI ecoAPI;

    protected PluginCoreAPI() {
        reflectAPI = new Reflections();
        gitHubAPI = new GitHub();
        materialAPI = new MaterialUtil();
        locationAPI = new LocationUtil();
        ecoAPI = new EcoUtil();
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
