package org.sct.plugincore.util.plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:45
 */

public class GetUpdateDetail {

    public static void get(CommandSender sender, JavaPlugin instance) throws IOException {
        String pluginName = instance.getDescription().getName();

        String detail = GitHubAPI.getReleaseDetail("LovesAsuna", pluginName).replaceAll("\\r", "\\n");
        sender.sendMessage("§e===============================================");
        sender.sendMessage(detail);
        sender.sendMessage("§e===============================================");
    }

}