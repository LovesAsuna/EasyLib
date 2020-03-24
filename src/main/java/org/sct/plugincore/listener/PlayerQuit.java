package org.sct.plugincore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.sct.plugincore.util.function.stack.StackTrace;
import org.sct.plugincore.util.player.InvUtil;

import java.io.IOException;

/**
 * @author LovesAsuna
 * @date 2020/3/3 18:18
 */

public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayer(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        try {
            InvUtil.store(player);
        } catch (IOException ex) {
            StackTrace.printStackTrace(ex);
        }
    }
}
