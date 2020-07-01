package org.sct.easylib.util.function.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.easylib.util.BasicUtil;

import java.io.File;
import java.util.List;

/**
 * 使用此类时请至少实例化一次对象
 * @author LovesAsuna
 * @date 2020/7/1 9:19
 */

public abstract class AbstractFile {
    private static File file;
    private static String fileName;
    private static JavaPlugin instance;
    protected static YamlConfiguration config;

    public static void load(File filePath, JavaPlugin instance) {
        AbstractFile.instance = instance;
        file = new File(instance.getDataFolder() + File.separator + filePath);
        fileName = filePath.getName();
        load();
    }

    public static void load() {
        if (file == null) {
            throw new NullPointerException("File can not be null");
        }
        if (!file.exists()) {
            instance.saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static String getString(String path) {
        load();
        String string = config.getString(path);
        return string == null ? "Empty String" : string;
    }

    public static List<String> getStringList(String path) {
        load();
        return BasicUtil.convert(config.getStringList(path));
    }

    public static double getDouble(String path) {
        load();
        return config.getDouble(path);
    }

    public static int getInt(String path) {
        return (int) getDouble(path);
    }

    public static float getFloat(String path) {
        return (float) config.getDouble(path);
    }
}
