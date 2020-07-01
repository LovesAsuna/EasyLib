package org.sct.easylib.util.function.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.easylib.util.BasicUtil;

import java.io.File;
import java.util.List;

/**
 * @author LovesAsuna
 * @date 2020/7/1 9:19
 */

public abstract class AbstractFile {
    private File file;
    private String fileName;
    private JavaPlugin instance;
    private YamlConfiguration config;

    public AbstractFile(File filePath, JavaPlugin instance) {
        this.instance = instance;
        file = new File(instance.getDataFolder() + File.separator + filePath);
        fileName = filePath.getName();
        load();

    }

    public void load() {
        if (!file.exists()) {
            instance.saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public String getString(String path) {
        load();
        String string = config.getString(path);
        return string == null ? "Empty String" : string;
    }

    public List<String> getStringList(String path) {
        load();
        return BasicUtil.convert(config.getStringList(path));
    }

    public double getDouble(String path) {
        load();
        return config.getDouble(path);
    }

    public int getInt(String path) {
        return (int) getDouble(path);
    }

    public float getFloat(String path) {
        return (float) config.getDouble(path);
    }
}
