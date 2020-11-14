package org.sct.easylib.util.function.command;

import org.bukkit.command.CommandSender;

import java.util.Map;

public interface SubCommand {
    /**
     * 指令调用接口
     *
     * @param sender 命令发送者
     * @param args 命令参数
     * @return 命令是否调用成功
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * 快速命令补全
     *
     * @return map Integer为参数的位置(从1开始),String[]为该位置拥有的命令
     **/
    default Map<Integer, String[]> getParams() {
        return null;
    }

}
