package org.sct.easylib.api;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public interface GitHubAPI {
    String getAPI(String author, String repos);

    String getRelease(String author, String repos, String auth);

    String getReleaseDetail(String author, String repos, String auth);

    String getNewestVersion(String author, String repos, String auth);

    void getUpdateDetail(CommandSender sender, JavaPlugin instance, String author, String auth) throws IOException;

    boolean download(CommandSender sender, JavaPlugin instance, String author);
}
