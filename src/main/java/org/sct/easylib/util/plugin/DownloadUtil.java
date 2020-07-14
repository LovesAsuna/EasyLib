package org.sct.easylib.util.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:45
 */

public class DownloadUtil {
    /**
     * @param urlString 下砸地址
     * @param fileName 不包含路径的文件名
     * @param savePath 不包含文件名的路径
     * @param consumer 消费者函数，对下载时的字节长度进行处理
     * @param heads 请求头
     */
    public static boolean download(String urlString, String fileName, String savePath, Consumer<Integer> consumer, String[]... heads) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            for (String[] head : heads) {
                conn.setRequestProperty(head[0], head[1]);
            }
            conn.connect();
            download(conn, fileName, savePath, consumer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * @param conn 已经设置好属性并已经连接的HttpURLConnection
     * @param fileName 不包含路径的文件名
     * @param savePath 不包含文件名的路径
     * @param consumer 消费者函数，对下载时的字节长度进行处理
     */
    public static void download(HttpURLConnection conn, String fileName, String savePath, Consumer<Integer> consumer) throws IOException {
        download(conn, new File(savePath + File.separator + fileName), consumer);
    }

    /**
     * @param conn 已经设置好属性并已经连接的HttpURLConnection
     * @param file 文件绝对路径
     * @param consumer 消费者函数，对下载时的字节长度进行处理
     */
    public static void download(HttpURLConnection conn, File file, Consumer<Integer> consumer) throws IOException {
        InputStream inputStream = conn.getInputStream();
        int length = 0;
        if (consumer == null) {
            consumer = i -> {};
        }
        byte[] bytes = new byte[2048];
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while ((length = inputStream.read(bytes)) != -1) {
            consumer.accept(length);
            fileOutputStream.write(bytes, 0, length);
        }
        fileOutputStream.close();
        conn.disconnect();
    }
}
