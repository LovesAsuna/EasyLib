package org.sct.easylib.util.plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.easylib.EasyLib;
import org.sct.easylib.data.LibData;

import java.io.IOException;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:44
 */

public class CheckUpdate {

    public static void check(CommandSender sender, JavaPlugin instance, String author, String auth) {
        if (!LibData.getAutoupdate()) {
            return;
        }
        String pluginName = instance.getDescription().getName();
        try {
            String newestVersion = EasyLib.getEasyLibAPI().getGitHubAPI().getNewestVersion(author, pluginName, auth);
            if (newestVersion == null) {
                return;
            }
            String currentVersion = instance.getDescription().getVersion();
            LibData.getNewestversion().put(instance.getName(), newestVersion);

            if (currentVersion.equalsIgnoreCase(newestVersion)) {
                instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7] §2你正在使用最新的" + currentVersion + "版本");
            } else {
                instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7] §c最新版本为" + LibData.getNewestversion().get(instance.getName()));
                if (EasyLib.getInstance().getConfig().getBoolean("ShowUpdateMsg")) {
                    EasyLib.getEasyLibAPI().getGitHubAPI().getUpdateDetail(sender, instance, author, auth);
                }
                instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7] §c请下载更新!");
            }
        } catch (IOException e) {
            instance.getServer().getConsoleSender().sendMessage("§7[§e" + pluginName + "§7] §c插件在检测版本时发生错误!");
        }
    }

}