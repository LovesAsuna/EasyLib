package org.sct.easylib.util.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.easylib.EasyLib;
import org.sct.easylib.data.LibData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:07
 */

public class GitHub implements org.sct.easylib.api.GitHubAPI {
    @Override
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

    @Override
    public String getRelease(String author, String repos, String auth) {
        StringBuffer buffer = new StringBuffer();
        String line;
        EasyLib.getInstance().getServer().getConsoleSender().sendMessage(String.format("§7[§e%s§7] §cThere was an issue attempting to check for the latest version.", repos));

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
        String release = getRelease(author, repos, auth);
        ObjectMapper mapper = LibData.getObjectMapper();
        try {
            JsonNode root = mapper.readTree(release);
            return root.get(0).get(path).asText();
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public String getReleaseDetail(String author, String repos, String auth) {
        return getReleaseInfo(author, repos, "body", auth);
    }

    @Override
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

    @Override
    public void getUpdateDetail(CommandSender sender, JavaPlugin instance, String author, String auth) throws IOException {
        String pluginName = instance.getDescription().getName();

        String detail = getReleaseDetail(author, pluginName, auth).replaceAll("\\r", "\\n");
        sender.sendMessage("§e===============================================");
        sender.sendMessage(detail);
        sender.sendMessage("§e===============================================");
    }

    @Override
    public boolean download(CommandSender sender, JavaPlugin instance, String author) {
        String pluginName = instance.getDescription().getName();
        Bukkit.getScheduler().runTaskAsynchronously(EasyLib.getInstance(), () -> {
            String savePath = instance.getDataFolder().getPath() + "\\update";
            String fileName = pluginName + ".jar";
            DownloadUtil.download("https://github.com/" + author + "/" + pluginName + "/releases/download/" + LibData.getNewestversion().get(instance.getName()) + "/" + pluginName + ".jar", fileName, savePath);
            sender.sendMessage("§7[§e" + pluginName + "§7]§2文件下载成功，已保存在" + savePath + File.separator + fileName);
            return;
        });
        return true;
    }
}
