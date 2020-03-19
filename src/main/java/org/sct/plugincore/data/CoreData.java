package org.sct.plugincore.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.sct.plugincore.util.function.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author icestar
 * @date 2020/2/15 20:11
 */

public class CoreData {

    @Getter
    private static Map<String, String> newestversion;
    @Setter
    @Getter
    private static String author;
    @Getter
    private static ObjectMapper mapper;

    /*事件抑制器*/
    @Getter
    private static Map<OfflinePlayer, Boolean> inhibition;

    /*插件专用计划线程池*/
    @Getter
    private static ScheduledThreadPoolExecutor scheduledpool;

    /*SQLite*/
    @Getter @Setter
    private static DataBase dataBase;

    static {
        newestversion = Maps.newHashMap();
        mapper = new ObjectMapper();
        inhibition = new HashMap<>();
        scheduledpool = new ScheduledThreadPoolExecutor(1);
    }
}
