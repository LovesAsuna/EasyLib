package org.sct.easylib.util.function.stack;

import org.bukkit.Bukkit;
import org.sct.easylib.util.BasicUtil;

/**
 * @author LovesAsuna
 * @date 2020/3/8 17:59
 */

public class StackTrace {

    public static void printStackTrace(Throwable exception) {
        printStackTrace(exception, null);
    }

    public static void printStackTrace(Throwable exception, String packageFilter) {
        print("========================= &c&lprintStackTrace &7=========================");
        print("Exception Type ▶");
        print("&c" + exception.toString());
        print("");
        String lastPackage = "";
        for (StackTraceElement elem : exception.getStackTrace()) {
            String key = elem.getClassName();

            boolean pass = true;
            if (packageFilter != null) {
                pass = key.contains(packageFilter);
            }

            String[] nameSet = key.split("[.]");
            String className = nameSet[nameSet.length - 1];
            String[] packageSet = new String[nameSet.length - 2];
            System.arraycopy(nameSet, 0, packageSet, 0, nameSet.length - 2);

            StringBuilder packageName = new StringBuilder();
            int counter = 0;
            for (String nameElem : packageSet) {
                packageName.append(nameElem);
                if (counter < packageSet.length - 1) {
                    packageName.append(".");
                }
                counter++;
            }

            if (pass) {
                if (!packageName.toString().equals(lastPackage)) {
                    lastPackage = packageName.toString();
                    print("");
                    print("Package &c" + packageName + " &7 ▶");
                }
                print("  ▶ at Class &c" + className + " &7, Method &c" + elem.getMethodName() + "&7. (&c" + elem.getFileName() + "&7, Line &c" + elem.getLineNumber() + "&7)");
            }
        }
        print("========================= &c&lprintStackTrace &7=========================");
    }

    private static void print(String message) {
        if (message != null) {
            try {
                Bukkit.getConsoleSender().sendMessage(BasicUtil.convert(message));
            } catch (NullPointerException e) {
                message = message.replaceAll("&([0-9]|[a-z])?", "");
                System.err.println(message);
            }
        }
    }


}
