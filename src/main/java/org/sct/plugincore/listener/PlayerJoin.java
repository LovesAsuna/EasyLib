package org.sct.plugincore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.sct.plugincore.util.player.InvUtil;

/**
 * @author LovesAsuna
 * @date 2020/3/3 18:23
 */

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        InvUtil.saveInv(e.getPlayer());
    }
}
