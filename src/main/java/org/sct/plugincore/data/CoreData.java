package org.sct.plugincore.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.sct.plugincore.api.DataBaseManager;

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

    /*事件抑制器*/
    @Getter
    private static Map<OfflinePlayer, Boolean> inhibition;

    /*插件专用计划线程池*/
    @Getter
    private static ScheduledThreadPoolExecutor scheduledpool;

    /*SQLite*/
    @Getter
    @Setter
    private static DataBaseManager dataBaseManager;

    /*自动更新 shezhi*/
    @Getter
    @Setter
    private static Boolean autoupdate;

    @Getter
    @Setter
    private static ObjectMapper objectMapper;

    static {
        newestversion = Maps.newHashMap();
        inhibition = new HashMap<>();
        scheduledpool = new ScheduledThreadPoolExecutor(1);
    }
}
