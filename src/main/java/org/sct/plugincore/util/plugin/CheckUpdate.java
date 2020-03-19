package org.sct.plugincore.util.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.plugincore.data.CoreData;

import java.io.IOException;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:44
 */

public class CheckUpdate {

    public static void check(CommandSender sender, JavaPlugin instance) {
        String pluginName = instance.getDescription().getName();
        try {
            String release = GitHubAPI.getRelease("LovesAsuna", pluginName);

            JsonNode root = CoreData.getMapper().readTree(release);

            String newestVersion = root.get(0).get("tag_name").asText();
            String currentVersion = instance.getDescription().getVersion();

            CoreData.getNewestversion().put(instance.getName(), newestVersion);


            if (currentVersion.equalsIgnoreCase(newestVersion)) {
                instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7]§2你正在使用最新的" + currentVersion + "版本");
            } else {
                instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7]§c最新版本为" + CoreData.getNewestversion().get(instance.getName()));
                GetUpdateDetail.get(sender, instance);
                instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7]§c请下载更新!");
            }
        } catch (IOException e) {
            instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7]§c插件在检测版本时发生错误!");
        }
    }

}