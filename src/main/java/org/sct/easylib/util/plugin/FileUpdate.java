package org.sct.easylib.util.plugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
            JarFile jarFile = new JarFile(new File(jarPath));

            File currentFile = new File(path + File.separator + fileName);

            Optional<JarEntry> optional = jarFile.stream().parallel().filter(s -> s.getName().equalsIgnoreCase(fileName)).findFirst();
            if (!optional.isPresent()) {
                throw new IllegalArgumentException("wrong file");
            }
            Reader reader = new InputStreamReader(jarFile.getInputStream(optional.get()));

            YamlConfiguration orign = YamlConfiguration.loadConfiguration(reader);
            Set<String> originKeys = Objects.requireNonNull(orign.getConfigurationSection("")).getKeys(true);

            YamlConfiguration current = YamlConfiguration.loadConfiguration(currentFile);

            int currentSize = getSize(current);
            int originSize = getSize(orign);
            if (currentSize < originSize) {
                originKeys.forEach(originString -> {
                    Set<String> keys = Objects.requireNonNull(current.getConfigurationSection("")).getKeys(true);
                    if (keys.parallelStream().noneMatch(s -> s.equals(originString))) {
                        current.set(originString, orign.get(originString));
                    }
                });
                current.save(currentFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§7[§eEasyLib§7]§c自动更新配置文件时出错!");
        }
    }

    private static int getSize(Configuration configuration) {
        return Objects.requireNonNull(configuration.getConfigurationSection("")).getKeys(true).size();
    }

}
