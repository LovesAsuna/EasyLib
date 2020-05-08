package org.sct.easylib;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.easylib.data.LibData;
import org.sct.easylib.manager.ListenerManager;
import org.sct.easylib.util.plugin.Dependence;
import org.sct.easylib.util.plugin.FileUpdate;
import org.sct.easylib.util.plugin.Metrics;

import java.io.File;
import java.util.List;

/**
 * @author LovesAsuna
 * @date 2020/2/15 20:04
 */

public class EasyLib extends JavaPlugin {

    @Getter
    private static EasyLib instance;
    @Setter
    public static EasyLibAPI easyLibAPI;

    @Override
    public void onEnable() {
        instance = this;
        new Metrics(this, 6909);
        Dependence.init();
        saveDefaultConfig();
        LibData.setAutoupdate(getConfig().getBoolean("AutoUpdate"));
        ListenerManager.registerListener();
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            FileUpdate.update(this, "config.yml", getDataFolder().getPath());
        });
        Bukkit.getScheduler().runTaskAsynchronously(this, this::getHookPlugins);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§7[§eEasyLib§7] §cDisabled!");
    }

    private void getHookPlugins() {
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        String pluginName = instance.getDescription().getName();
        List<String> myDepend = Lists.newArrayList();
        List<String> mySoftDepend = Lists.newArrayList();
        for (Plugin plugin : plugins) {
            if (plugin.getName().equalsIgnoreCase(pluginName)) {
                continue;
            }
            String path = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
            List<String> pluginDepend;
            List<String> pluginSoftDepeng;
            try {
                pluginDepend = plugin.getPluginLoader().getPluginDescription(new File(path)).getDepend();
                pluginDepend.stream().parallel().forEach(depend -> {
                    if (depend.equalsIgnoreCase(pluginName)) {
                        myDepend.add(plugin.getName());
                    }
                });
                pluginSoftDepeng = plugin.getPluginLoader().getPluginDescription(new File(path)).getSoftDepend();
                pluginSoftDepeng.stream().parallel().forEach(softDepend -> {
                    if (softDepend.equalsIgnoreCase(pluginName)) {
                        mySoftDepend.add(plugin.getName());
                    }
                });
            } catch (InvalidDescriptionException e) {
            }
        }
        StringBuffer depend = new StringBuffer();
        myDepend.stream().forEach(d -> {
            depend.append("§a").append(d).append(" ");
        });
        if (depend.length() != 0) {
            Bukkit.getConsoleSender().sendMessage("§7[§eEasyLib§7]§bDepend: " + depend.toString());
        }

        StringBuffer softDepend = new StringBuffer();
        mySoftDepend.parallelStream().forEach(d -> {
            softDepend.append("§a").append(d).append(" ");
        });

        if (softDepend.length() != 0) {
            Bukkit.getConsoleSender().sendMessage("§7[§eEasyLib§7]§bSoftDepend: " + softDepend.toString());
        }
    }

    public static EasyLibAPI getEasyLibAPI() {
        try {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(250);
                if (easyLibAPI != null) {
                    return easyLibAPI;
                }
            }
            return null;
        } catch (InterruptedException e) {
            return null;
        }
    }

}
