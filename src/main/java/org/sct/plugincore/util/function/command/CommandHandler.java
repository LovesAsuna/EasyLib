package org.sct.plugincore.util.function.command;

import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author SCT_Alchemy
 * @date 2018/12/21 12:46 PM
 */

public class CommandHandler implements TabExecutor {
    protected String cmd;
    protected JavaPlugin instance;
    protected Map<String, SubCommand> subCommandMap = Maps.newHashMap();

    public CommandHandler(JavaPlugin instance, String cmd) {
        this.cmd = cmd;
        this.instance = instance;
    }

    public void registerSubCommand(String commandName, SubCommand command) {
        if (subCommandMap.containsKey(commandName)) {
            instance.getLogger().warning("发现重复子命令注册!");
        }
        subCommandMap.put(commandName, command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.cmd.equalsIgnoreCase(cmd.getName())) {
            if (args.length == 0) {//如果命令没有参数
                subCommandMap.get("info").execute(sender, args);
                return true;
            }

            SubCommand subCommand = subCommandMap.get(args[0]);
            if (subCommand == null) {//如果参数不正确
                sender.sendMessage("");
                return true;
            }
            subCommand.execute(sender, args);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (cmd.getName().equalsIgnoreCase(this.cmd)) {
            if (args.length == 1) {
                completions.addAll(Arrays.asList(subCommandMap.keySet().toArray(new String[0])));
                return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
            } else {
                SubCommand subCommand = subCommandMap.get(args[0]);
                if (subCommand != null) {
                    Map<Integer, String[]> paramsMap = subCommand.getParams();
                    if (paramsMap != null) {
                        String[] params = paramsMap.get(args.length - 1);
                        if (params != null) {
                            completions.addAll(Arrays.asList(params));
                            return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
                        }
                    }
                }
                return completions;
            }
        }
        return completions;
    }
}


