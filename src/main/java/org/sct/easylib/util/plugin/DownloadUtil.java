package org.sct.easylib.util.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LovesAsuna
 * @date 2020/2/17 16:45
 */

public class DownloadUtil {
    public static boolean download(String urlString, String fileName, String savePath, String[]... heads) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            for (String[] head : heads) {
                conn.setRequestProperty(head[0], head[1]);
            }
            conn.connect();
            download(conn, fileName, savePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void download(HttpURLConnection conn, String fileName, String savePath) throws IOException {
        download(conn, new File(savePath + File.separator + fileName));
    }

    public static void download(HttpURLConnection conn, File file) throws IOException {
        InputStream inputStream = conn.getInputStream();
        int length = 0;
        byte[] bytes = new byte[2048];
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while ((length = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, length);
        }
        fileOutputStream.close();
        conn.disconnect();
    }
}
