package org.sct.easylib;

import lombok.Getter;
import org.sct.easylib.api.*;
import org.sct.easylib.api.DataBaseManager;
import org.sct.easylib.util.function.database.MysqlUtil;
import org.sct.easylib.util.function.database.SQLiteUtil;
import org.sct.easylib.util.function.econoomy.EcoUtil;
import org.sct.easylib.util.function.item.MaterialUtil;
import org.sct.easylib.util.function.player.LocationUtil;
import org.sct.easylib.util.function.serializer.BukkitObjectSerializerUtils;
import org.sct.easylib.util.plugin.GitHub;
import org.sct.easylib.util.reflectutil.Reflections;

/**
 * @author LovesAsuna
 * @date 2020/3/23 20:53
 */

public class EasyLibAPI {

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
    @Getter
    private SerializerAPI serializerAPI;

    public EasyLibAPI() {
        reflectAPI = new Reflections();
        gitHubAPI = new GitHub();
        materialAPI = new MaterialUtil();
        locationAPI = new LocationUtil();
        ecoAPI = new EcoUtil();
        serializerAPI = new BukkitObjectSerializerUtils();
    }

    public DataBaseManager getDataBaseManager(dataBaseType type,String url) {
        if (type == dataBaseType.MYSQL) {
            return new MysqlUtil(url);
        } else if (type == dataBaseType.SQLITE) {
            return new SQLiteUtil(url);
        } else {
            return null;
        }
    }

    public enum dataBaseType {
        MYSQL,
        SQLITE
    }

}
