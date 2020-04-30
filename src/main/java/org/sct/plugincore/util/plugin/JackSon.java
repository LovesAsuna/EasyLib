package org.sct.plugincore.util.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.sct.plugincore.PluginCore;
import org.sct.plugincore.PluginCoreAPI;
import org.sct.plugincore.util.BasicUtil;
import org.sct.plugincore.util.function.stack.StackTrace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LovesAsuna
 * @date 2020/4/6 13:59
 */

public class JackSon {
    @Getter
    private static ObjectMapper ObjectMapper;
    @Getter
    private static boolean downloadDepen = false;

    private static final String ANNOTATIONS_MD5 = "20368d1f52e031381a510cd1ce6ea2b7";
    private static final String CORE_MD5 = "8f84e33a1c06b8fd16b4166b9fc8331b";
    private static final String DATABIND_MD5 = "f96c78787ea2830e8dfd3a5a66c4f664";

    public static void initJackson() {
        if (downloadDepen) {
            return;
        }
        downloadDepen = true;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1));
        File depenDir = new File(PluginCore.getInstance().getDataFolder() + File.separator + "Dependencies");
        if (!depenDir.exists()) {
            try {
                Files.createDirectory(Paths.get(depenDir.getPath()));
            } catch (IOException e) {
                StackTrace.printStackTrace(e);
                return;
            }
        }
        File databindFile = new File(PluginCore.getInstance().getDataFolder() + File.separator + "Dependencies" + File.separator + "jackson-databind-2.10.3.jar");
        File coreFile = new File(PluginCore.getInstance().getDataFolder() + File.separator + "Dependencies" + File.separator + "jackson-core-2.10.3.jar");
        File annotationsFile = new File(PluginCore.getInstance().getDataFolder() + File.separator + "Dependencies" + File.separator + "jackson-annotations-2.10.3.jar");

        AtomicReference<HttpURLConnection> databindconn = new AtomicReference<>();
        AtomicReference<HttpURLConnection> coreconn = new AtomicReference<>();
        AtomicReference<HttpURLConnection> annotationsconn = new AtomicReference<>();

        AtomicReference<URL> databind = new AtomicReference<>();
        AtomicReference<URL> annotations = new AtomicReference<>();
        AtomicReference<URL> core = new AtomicReference<>();
        try {
            databindconn.set((HttpURLConnection) new URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.10.3/jackson-databind-2.10.3.jar").openConnection());
            coreconn.set((HttpURLConnection) new URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.10.3/jackson-core-2.10.3.jar").openConnection());
            annotationsconn.set((HttpURLConnection) new URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.10.3/jackson-annotations-2.10.3.jar").openConnection());

            databind.set(databindFile.toURI().toURL());
            annotations.set(annotationsFile.toURI().toURL());
            core.set(coreFile.toURI().toURL());
        } catch (IOException e) {
            StackTrace.printStackTrace(e);
        }

        AtomicBoolean databindfinish = new AtomicBoolean(false);
        AtomicBoolean annotationsfinish = new AtomicBoolean(false);
        AtomicBoolean corefinish = new AtomicBoolean(false);

        pool.submit(() -> {
            String s = "jackson databind";
            if (!databindFile.exists()) {
                sendLackMessage(s);
                download(databindconn, databindFile);
                databindfinish.set(true);
            } else {
                if (!DATABIND_MD5.equals(BasicUtil.getFileMD5(databindFile))) {
                    sendInCompleteMessage(s);
                    download(databindconn, databindFile);
                }
                databindfinish.set(true);
            }
        });

        pool.submit(() -> {
            String s = "jackson core";
            if (!coreFile.exists()) {
                sendLackMessage(s);
                download(coreconn, coreFile);
                corefinish.set(true);
            } else {
                if (!CORE_MD5.equals(BasicUtil.getFileMD5(coreFile))) {
                    sendInCompleteMessage(s);
                    download(coreconn, coreFile);
                }
                corefinish.set(true);
            }
        });

        pool.submit(() -> {
            String s = "jackson annotations";
            if (!annotationsFile.exists()) {
                sendLackMessage(s);
                download(annotationsconn, annotationsFile);
                annotationsfinish.set(true);
            } else {
                if (!ANNOTATIONS_MD5.equals(BasicUtil.getFileMD5(annotationsFile))) {
                    sendInCompleteMessage(s);
                    download(annotationsconn, annotationsFile);
                }
                annotationsfinish.set(true);
            }
        });


        pool.submit(() -> {
            while (true) {
                if (databindfinish.get() && corefinish.get() && annotationsfinish.get()) {
                    break;
                }
            }

            try {
                Method addURL = Class.forName("java.net.URLClassLoader").getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                addURL.invoke(ClassLoader.getSystemClassLoader(), databind.get());
                addURL.invoke(ClassLoader.getSystemClassLoader(), core.get());
                addURL.invoke(ClassLoader.getSystemClassLoader(),  annotations.get());
                ObjectMapper = new ObjectMapper();
                PluginCore.setPluginCoreAPI(new PluginCoreAPI());
            } catch (ReflectiveOperationException e) {
                StackTrace.printStackTrace(e);
            }

        });
    }

    private static void download(AtomicReference<HttpURLConnection> conn, File file) {
        try {
            conn.get().connect();
            InputStream in = conn.get().getInputStream();
            byte[] bytes = new byte[3072];
            int length;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while ((length = in.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, length);
            }
            fileOutputStream.close();
            sendDownloadCompleteMessage(file.getName());
        } catch (IOException e) {
            StackTrace.printStackTrace(e);
        }
    }

    private static void sendLackMessage(String dependency) {
        sendMessage(String.format("§7[§ePluginCore§7]§cLack of dependency: %s, §bstart downloading!", dependency));
    }

    private static void sendDownloadCompleteMessage(String dependency) {
        sendMessage(String.format("§7[§ePluginCore§7]§2Dependency: %s download completed!", dependency));
    }

    private static void sendInCompleteMessage(String dependency) {
        sendMessage(String.format("§7[§ePluginCore§7]§cDependency: %s is incomplete, §bstart downloading!", dependency));
    }

    private static void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(message);
    }
}
