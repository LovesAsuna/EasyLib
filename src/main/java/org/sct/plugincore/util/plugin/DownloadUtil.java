package org.sct.plugincore.util.plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.sct.plugincore.data.CoreData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:45
 */

public class DownloadUtil {

    public static void download(CommandSender sender, JavaPlugin instance, String author) throws IOException {
        String pluginName = instance.getDescription().getName();
        download(sender, instance, "https://github.com/" + author + "/" + pluginName + "/releases/download/" + CoreData.getNewestversion().get(instance.getName()) + "/" + pluginName + ".jar", pluginName + ".jar", instance.getDataFolder().getPath() + "\\update");
    }

    public static void download(CommandSender sender, JavaPlugin instance, String urlString, String fileName, String savePath) throws IOException {
        String pluginName = instance.getDescription().getName();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        /*设置超时时间为3秒*/
        conn.setConnectTimeout(3 * 1000);
        /*建立连接*/
        sender.sendMessage("§7[§e" + pluginName + "§7]§2与下载源建立连接中.....(版本: " + CoreData.getNewestversion().get(instance.getName()) + ")");
        conn.connect();
        /*得到输入流*/
        InputStream inputStream = conn.getInputStream();
        /*获取字节数组*/
        sender.sendMessage("§7[§e" + pluginName + "§7]§2获取文件字节流中.....");
        byte[] getData = readInputStream(inputStream);

        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        sender.sendMessage("§7[§e" + pluginName + "§7]§2文件输出中.....");
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        conn.disconnect();
        sender.sendMessage("§7[§e" + pluginName + "§7]§2文件下载成功，已保存在" + savePath.toString() + File.separator + fileName);
    }

    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
