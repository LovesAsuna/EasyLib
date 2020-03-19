package org.sct.plugincore.util.function;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.sct.plugincore.data.CoreData;

import java.util.concurrent.TimeUnit;

/**
 * @author LovesAsuna
 * @date 2020/2/20 19:08
 */

public class Inhibition {
    public static boolean getInhibitStatus(OfflinePlayer player, int delay, TimeUnit timeUnit) {
        if (CoreData.getInhibition().get(player) != null) {
            return false;
        } else {
            CoreData.getInhibition().put(player,true);
            CoreData.getScheduledpool().schedule(() -> {
                CoreData.getInhibition().remove(player);
            }, delay, timeUnit);
            return true;
        }
    }

    public static boolean getInhibitStatus(int delay, TimeUnit timeUnit) {
        return getInhibitStatus(Bukkit.getOfflinePlayer("Native"), delay, timeUnit);
    }
}
