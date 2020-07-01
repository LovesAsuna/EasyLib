package org.sct.easylib.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * @author LovesAsuna
 * @date 2020/7/1 10:05
 */

public class ListenerManager {
    private static void register(Listener listener, JavaPlugin instance) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void registerListener(Listener[] listeners, JavaPlugin instance) {
        Arrays.asList(listeners).forEach(listener -> register(listener, instance));
    }
}