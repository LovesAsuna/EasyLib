package org.sct.easylib.util.reflectutil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author LovesAsuna
 * @date 2020/2/27 19:13
 */

public class ClassUtil {

    protected ClassUtil() {
    }

    public static Class<?> getInstanceClass(String path, String instanceName) {
        try {
            InputStream inputStream = new FileInputStream(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(bytes);

            Class<?> classLoader = Class.forName("java.lang.ClassLoader");
            Method method = classLoader.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            method.setAccessible(true);
            Class<?> c = (Class<?>) method.invoke(ClassLoader.getSystemClassLoader(), instanceName, bytes, 0, bytes.length);
            return c;
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getInstance(String path, String instanceName) {
        Class<?> c = getInstanceClass(path, instanceName);
        try {
            return c.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean forceLoad(Class<?> className) {
        try {
            Class.forName(className.getName());
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
