package org.sct.plugincore.util.plugin;

import lombok.Getter;
import org.sct.plugincore.PluginCore;
import org.sct.plugincore.util.function.stack.StackTrace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
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
    private static Object ObjectMapper;
    @Getter
    private static Method readTree;
    @Getter
    private static Method intget;
    @Getter
    private static Method stringget;
    @Getter
    private static Method asText;
    @Getter
    private static boolean downloadDepen = false;

    public static void download() {
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
            coreconn.set((HttpURLConnection) new URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.10.3/jackson-databind-2.10.3.jar").openConnection());
            annotationsconn.set((HttpURLConnection) new URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.10.3/jackson-annotations-2.10.3.jar").openConnection());

            databind.set(new URL("file:/" + databindFile.getAbsolutePath()));
            annotations.set(new URL("file:/" + annotationsFile.getAbsolutePath()));
            core.set(new URL("file:/" + coreFile.getAbsolutePath()));
        } catch (IOException e) {
            StackTrace.printStackTrace(e);
        }

        AtomicBoolean databindfinish = new AtomicBoolean(false);
        AtomicBoolean annotationsfinish = new AtomicBoolean(false);
        AtomicBoolean corefinish = new AtomicBoolean(false);

        pool.submit(() -> {
            if (!databindFile.exists()) {
                try {
                    databindconn.get().connect();
                    InputStream in = databindconn.get().getInputStream();
                    byte[] bytes = new byte[3072];
                    int length;
                    FileOutputStream fileOutputStream = new FileOutputStream(databindFile);
                    while ((length = in.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    fileOutputStream.close();
                } catch (IOException e) {
                    StackTrace.printStackTrace(e);
                }
                databindfinish.set(true);
            } else {
                databindfinish.set(true);
            }
        });

        pool.submit(() -> {
            if (!coreFile.exists()) {
                try {
                    coreconn.get().connect();
                    InputStream in = coreconn.get().getInputStream();
                    byte[] bytes = new byte[3072];
                    int length;
                    FileOutputStream fileOutputStream = new FileOutputStream(coreFile);
                    while ((length = in.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    fileOutputStream.close();
                } catch (IOException e) {
                    StackTrace.printStackTrace(e);
                }
                corefinish.set(true);
            } else {
                corefinish.set(true);
            }

        });

        pool.submit(() -> {
            if (!annotationsFile.exists()) {
                try {
                    annotationsconn.get().connect();
                    InputStream in = annotationsconn.get().getInputStream();
                    byte[] bytes = new byte[3072];
                    int length;
                    FileOutputStream fileOutputStream = new FileOutputStream(annotationsFile);
                    while ((length = in.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    fileOutputStream.close();
                } catch (IOException e) {
                    StackTrace.printStackTrace(e);
                }
                annotationsfinish.set(true);
            } else {
                annotationsfinish.set(true);
            }
        });


        pool.submit(() -> {
            while (!databindfinish.get() || !corefinish.get() || !annotationsfinish.get()) {

            }

            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{databind.get(), core.get(), annotations.get()}, ClassLoader.getSystemClassLoader());
            try {
                Class<?> ObjectMapperClass = urlClassLoader.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
                Class<?> JsonNode = urlClassLoader.loadClass("com.fasterxml.jackson.databind.JsonNode");

                Constructor<?> constructor = ObjectMapperClass.getDeclaredConstructor();

                readTree = ObjectMapperClass.getDeclaredMethod("readTree", String.class);
                intget = JsonNode.getDeclaredMethod("get", int.class);
                stringget = JsonNode.getDeclaredMethod("get", String.class);
                asText = JsonNode.getDeclaredMethod("asText");

                ObjectMapper = constructor.newInstance();
            } catch (ReflectiveOperationException e) {
                StackTrace.printStackTrace(e);
            }

        });
    }
}
