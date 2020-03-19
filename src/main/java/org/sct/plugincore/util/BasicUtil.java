package org.sct.plugincore.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class BasicUtil {

    public static String convert(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> convert(List<String> message) {
        List<String> msgList = new ArrayList<>();
        for (String key : message) {
            msgList.add(convert(key));
        }
        return msgList;
    }

    public static String remove(String message) {
        return ChatColor.stripColor(message);
    }

    public static List<String> remove(List<String> message) {
        List<String> msgList = new ArrayList<>();
        for (String key : message) {
            msgList.add(remove(key));
        }
        return msgList;
    }

    public static int ExtraceInt(String string) {
        String s = string.trim();
        String collect = "";
        if (s != null && !"".equals(s)) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
                    if (s.charAt(i) == 38) {
                        continue;
                    }
                    collect += s.charAt(i);
                }
            }
        }
        return collect.isEmpty() ? 0 : Integer.parseInt(collect);
    }

    public static <T> String replace(String message, String var, T replace) {
        return message.replace(var, String.valueOf(replace));
    }

}
