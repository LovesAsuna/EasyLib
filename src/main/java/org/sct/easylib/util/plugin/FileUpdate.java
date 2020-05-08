package org.sct.easylib.util.plugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.easylib.EasyLib;
import org.sct.easylib.util.function.stack.StackTrace;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LovesAsuna
 * @date 2020/2/17 20:22
 */

public class FileUpdate {
    public static void update(JavaPlugin instance, String fileName, String path) {
        try {
            String jarPath = instance.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            if (System.getProperty("os.name").contains("Window")) {
                jarPath = jarPath.replaceFirst("/", "");
            }
            FileSystem system = null;

            system = FileSystems.newFileSystem(Paths.get(jarPath), null);
            String savePath = Paths.get(jarPath).getParent().toString() + File.separator + instance.getName() + "_" + fileName;

            File originFile = new File(savePath);
            File currentFile = new File(path + File.separator + fileName);

            deleteFile(originFile, savePath);
            Files.copy(system.getPath(fileName), Paths.get(savePath));

            YamlConfiguration orign = YamlConfiguration.loadConfiguration(originFile);
            Set<String> orignKeys = orign.getConfigurationSection("").getKeys(true);

            YamlConfiguration current = YamlConfiguration.loadConfiguration(currentFile);
            Set<String> currentKeys = orign.getConfigurationSection("").getKeys(true);

            int currentsize = getSize(current);
            int originSize = getSize(orign);
            if (currentsize < originSize) {
                orignKeys.stream().forEach(orignString -> {
                    AtomicBoolean contains = new AtomicBoolean(false);
                    Set<String> keys = instance.getConfig().getConfigurationSection("").getKeys(true);
                    keys.parallelStream().forEach(string -> {
                        if (orignString.equals(string)) {
                            contains.set(true);
                        }
                    });
                    if (!contains.get()) {
                        current.set(orignString, orign.get(orignString));
                    }
                });
                current.save(currentFile);
            }
            Bukkit.getScheduler().runTaskLaterAsynchronously(EasyLib.getInstance(), () -> {
                deleteFile(originFile, savePath);
            }, 20L);
        } catch (IOException e) {
            StackTrace.printStackTrace(e);
            Bukkit.getConsoleSender().sendMessage("§7[§eEasyLib§7]§c自动更新配置文件时出错!");
        }
    }

    private static boolean isSection(String path) {
        return EasyLib.getInstance().getConfig().getConfigurationSection(path) == null ? false : true;
    }

    private static int getSize(Configuration configuration) {
        return configuration.getConfigurationSection("").getKeys(true).size();
    }

    private static boolean deleteFile(File file, String savePath) {
        if (file.exists()) {
            try {
                Files.delete(Paths.get(savePath));
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
