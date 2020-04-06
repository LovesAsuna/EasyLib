package org.sct.plugincore.util.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.plugincore.PluginCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:07
 */

public class GitHubAPI {
    public String getAPI(String author, String repos) {
        StringBuffer buffer = new StringBuffer();
        String line;

        try {
            InputStream inputStream = getInputStream(author, repos);
            if (inputStream == null) {
                throw new NullPointerException();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException | NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage("§7[§e" + repos + "§7]§c获取API时发生错误");
            return null;
        }
        return buffer.toString();
    }

    public String getRelease(String author, String repos, String auth) {
        StringBuffer buffer = new StringBuffer();
        String line;

        try {
            InputStream inputStream = getInputStream(author, repos, auth);
            if (inputStream == null) {
                throw new NullPointerException();
        }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException | NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage("§7[§e" + repos + "§7]§c获取Release时发生错误");
            return null;
        }
        return buffer.toString();
    }

    private String getReleaseInfo(String author, String repos, String path, String auth) {
        String release = PluginCore.getPluginCoreAPI().getGitHubAPI().getRelease(author, repos, auth);
        Object ObjectMapper = JackSon.getObjectMapper();
        try {
            Object JsonNode = JackSon.getReadTree().invoke(ObjectMapper, release);
            return (String) JackSon.getAsText().invoke(JackSon.getStringget().invoke(JackSon.getIntget().invoke(JsonNode, 0), path));
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public String getReleaseDetail(String author, String repos, String auth) {
        return getReleaseInfo(author, repos, "body", auth);
    }

    public String getNewestVersion(String author, String repos, String auth) {
        return getReleaseInfo(author, repos, "tag_name", auth);
    }

    private InputStream getInputStream(String author, String repos, String auth) {
        InputStream inputStream;
        inputStream = getInputStream("https://api.github.com/repos/" + author + "/" + repos + "/releases", auth);
        return inputStream;
    }

    private InputStream getInputStream(String urlString, String auth) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            if (auth != null) {
                auth = new String(Base64.getDecoder().decode(auth));
                conn.setRequestProperty("Authorization", "token " + auth);
            }
            conn.connect();
            return conn.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public void getUpdateDetail(CommandSender sender, JavaPlugin instance, String auth) throws IOException {
        String pluginName = instance.getDescription().getName();

        String detail = getReleaseDetail("LovesAsuna", pluginName, auth).replaceAll("\\r", "\\n");
        sender.sendMessage("§e===============================================");
        sender.sendMessage(detail);
        sender.sendMessage("§e===============================================");
    }
}
