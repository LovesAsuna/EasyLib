package org.sct.easylib.util.function.command;

import org.bukkit.command.CommandSender;

import java.util.Map;

public interface SubCommand {
    /**
     * 指令调用接口
     *
     * @param sender
     * @param args
     * @return
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * @return map Integer为参数的位置(从1开始),String[]为该位置拥有的命令
     **/
    Map<Integer, String[]> getParams();

}
