package org.sct.easylib.util.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.sct.easylib.EasyLib;
import org.sct.easylib.EasyLibAPI;
import org.sct.easylib.data.CoreData;
import org.sct.easylib.data.DependenceData;
import org.sct.easylib.util.BasicUtil;
import org.sct.easylib.util.function.stack.StackTrace;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LovesAsuna
 * @date 2020/5/2 14:59
 */

public class Dependence {
    @Getter
    private final String MD5;
    @Getter
    private final String fileName;
    @Getter
    private final String url;
    @Getter
    private boolean finish = false;
    @Getter
    private final URL fileURL;
    private static boolean downloadDepen = false;
    private static final ThreadPoolExecutor pool;
    private static final File depenDir;

    static {
        pool = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));
        depenDir = new File(EasyLib.getInstance().getDataFolder() + File.separator + "Dependencies");
    }

    @SneakyThrows
    public Dependence(String fileName, DependenceData url, DependenceData MD5) {
        this.fileName = fileName;
        this.MD5 = MD5.getData();
        this.url = url.getData();
        fileURL = new File(depenDir.getPath() + File.separator + fileName).toURI().toURL();
    }


    public static void download(Dependence dependence) {
        pool.submit(() -> {
            if (!depenDir.exists()) {
                try {
                    Files.createDirectory(Paths.get(depenDir.getPath()));
                } catch (IOException e) {
                    StackTrace.printStackTrace(e);
                }
            }

            URL url = null;
            AtomicReference<HttpURLConnection> conn = new AtomicReference<>();
            try {
                url = new URL(dependence.getUrl());
                conn.set((HttpURLConnection) url.openConnection());
            } catch (IOException e) {
                StackTrace.printStackTrace(e);
            }
            File dependenceFile = new File(EasyLib.getInstance().getDataFolder() + File.separator + "Dependencies" + File.separator + dependence.getFileName());

            /*文件不存在*/
            if (!dependenceFile.exists()) {
                sendLackMessage(dependence.getFileName());
                download(conn, dependenceFile);
                dependence.finish = true;
            } else {
                /*文件存在*/
                if (!dependence.getMD5().equals(BasicUtil.getFileMD5(dependenceFile))) {
                    /*MD5不匹配*/
                    sendInCompleteMessage(dependence.getFileName());
                    download(conn, dependenceFile);
                }
                dependence.finish = true;
            }

        });
    }

    private static void download(AtomicReference<HttpURLConnection> conn, File file) {
        try {
            conn.get().connect();
            DownloadUtil.download(conn.get(), file);
            sendDownloadCompleteMessage(file.getName());
        } catch (IOException e) {
            StackTrace.printStackTrace(e);
        }
    }

    private static void sendLackMessage(String dependency) {
        sendMessage(String.format("§7[§eEasyLib§7]§cLack of dependency: %s, §bstart downloading!", dependency));
    }

    private static void sendDownloadCompleteMessage(String dependency) {
        sendMessage(String.format("§7[§eEasyLib§7]§2Dependency: %s download completed!", dependency));
    }

    private static void sendInCompleteMessage(String dependency) {
        sendMessage(String.format("§7[§eEasyLib§7]§cDependency: %s is incomplete, §bstart downloading!", dependency));
    }

    private static void sendLoadMessage(String dependency) {
        sendMessage(String.format("§7[§eEasyLib§7]§bDependency: %s §2load successfully!", dependency));
    }

    private static void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(message);
    }

    public static void init() {
        if (downloadDepen) {
            return;
        }
        downloadDepen = true;

        CoreData.getScheduledpool().execute(() -> {
            List<Dependence> dependences = new ArrayList<>();
            dependences.add(new Dependence("jackson-databind-2.10.3.jar", DependenceData.JACKSON_DATABIND_URL, DependenceData.JACKSON_DATABIND_MD5));
            dependences.add(new Dependence("jackson-core-2.10.3.jar", DependenceData.JACKSON_CORE_URL, DependenceData.JACKSON_CORE_MD5));
            dependences.add(new Dependence("jackson-annotations-2.10.3.jar", DependenceData.JACKSON_ANNOTATIONS_URL, DependenceData.JACKSON_ANNOTATIONS_MD5));

            if (EasyLib.getInstance().getConfig().getBoolean("Dependencies.Kotlin")) {
                dependences.add(new Dependence("annotations-19.0.0.jar", DependenceData.KOTLIN_STDLIB_ANNOTATIONS_URL, DependenceData.KOTLIN_STDLIB_ANNOTATIONS_MD5));
                dependences.add(new Dependence("kotlin-stdlib-1.3.72.jar", DependenceData.KOTLIN_STDLIB_URL, DependenceData.KOTLIN_STDLIB_MD5));
                dependences.add(new Dependence("kotlin-stdlib-common-1.3.72.jar", DependenceData.KOTLIN_STDLIB_COMMON_URL, DependenceData.KOTLIN_STDLIB_COMMON_MD5));
                dependences.add(new Dependence("kotlin-stdlib-jdk7-1.3.72.jar", DependenceData.KOTLIN_STDLIB_JDK7_URL, DependenceData.KOTLIN_STDLIB_JDK7_MD5));
                dependences.add(new Dependence("kotlin-stdlib-jdk8-1.3.72.jar", DependenceData.KOTLIN_STDLIB_JDK8_URL, DependenceData.KOTLIN_STDLIB_JDK8_MD5));
            }

            for (Dependence dependence : dependences) {
                Dependence.download(dependence);
            }


            while (true) {
                boolean finish = true;
                for (Dependence dependence : dependences) {
                    if (!dependence.isFinish()) {
                        finish = false;
                    }
                }

                if (finish) {
                    break;
                }
            }

            Method addURL = null;
            try {
                addURL = Class.forName("java.net.URLClassLoader").getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                for (Dependence dependence : dependences) {
                    addURL.invoke(EasyLib.class.getClassLoader(), dependence.getFileURL());
                    sendLoadMessage(dependence.getFileName());
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                StackTrace.printStackTrace(e);
            }

            CoreData.setObjectMapper(new ObjectMapper());
            EasyLib.setEasyLibAPI(new EasyLibAPI());
        });


    }

}
