package org.sct.plugincore.util.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.plugincore.data.CoreData;

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

    public String getRelease(String author, String repos) {
        StringBuffer buffer = new StringBuffer();
        String line;

        try {
            InputStream inputStream = getInputStream(author, repos);
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

    public String getReleaseDetail(String author, String repos) {
        String release = getRelease(author, repos);
        JsonNode root = null;

        try {
            root = CoreData.getMapper().readTree(release);
        } catch (JsonProcessingException e) {
            return null;
        }

        String body = root.get(0).get("body").asText();
        return body;
    }

    private InputStream getInputStream(String author, String repos) {
        InputStream inputStream;
        if (author.equalsIgnoreCase("LovesAsuna")) {
            inputStream = getInputStream("https://api.github.com/repos/" + author + "/" + repos + "/releases", true);
        } else {
            inputStream = getInputStream("https://api.github.com/repos/" + author + "/" + repos + "/releases", false);
        }
        return inputStream;
    }

    @SuppressWarnings("unchecked")
    private InputStream getInputStream(String urlString, boolean auth) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            if (auth) {
                conn.setRequestProperty("Authorization", "token " + new String(Base64.getDecoder().decode("ZTE0ZWNiZjc1ZDlmZWJkYTliNDBmZDJlYTQwZGIwODU1MDI2NTEwOA==")));
            }
            conn.connect();
            return conn.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public void getUpdateDetail(CommandSender sender, JavaPlugin instance) throws IOException {
        String pluginName = instance.getDescription().getName();

        String detail = getReleaseDetail("LovesAsuna", pluginName).replaceAll("\\r", "\\n");
        sender.sendMessage("§e===============================================");
        sender.sendMessage(detail);
        sender.sendMessage("§e===============================================");
    }
}
