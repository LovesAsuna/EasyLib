package org.sct.easylib.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.sct.easylib.EasyLib;
import org.sct.easylib.listener.PlayerJoin;
import org.sct.easylib.listener.PlayerQuit;

public class ListenerManager {
    private static void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, EasyLib.getInstance());
    }

    public static void registerListener() {
        register(new PlayerQuit());
        register(new PlayerJoin());
    }
}
