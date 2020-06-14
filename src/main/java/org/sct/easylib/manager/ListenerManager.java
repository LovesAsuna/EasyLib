package org.sct.easylib.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.sct.easylib.EasyLib;

public class ListenerManager {
    private static void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, EasyLib.getInstance());
    }

    public static void registerListener() {
        // register(new PlayerQuit());
        //  register(new PlayerJoin());
    }
}
