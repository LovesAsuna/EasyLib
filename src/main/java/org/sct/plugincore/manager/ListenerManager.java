package org.sct.plugincore.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.sct.plugincore.PluginCore;
import org.sct.plugincore.listener.PlayerJoin;
import org.sct.plugincore.listener.PlayerQuit;

public class ListenerManager {
    private static void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, PluginCore.getInstance());
    }

    public static void registerListener() {
        register(new PlayerQuit());
        register(new PlayerJoin());
    }
}
