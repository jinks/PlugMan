package com.ryanclancy000.plugman;

import com.ryanclancy000.plugman.utilities.Utilities;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlugManCommands implements CommandExecutor {

    private final PlugMan plugin;

    public PlugManCommands(PlugMan instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            plugin.getUtils().thisInfo(sender);
        } else if ("help".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.help")) {
                plugin.getUtils().helpList(sender);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("list".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.list")) {
                plugin.getUtils().listPlugins(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("vlist".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.vlist")) {
                plugin.getUtils().vlistPlugins(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("info".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.info")) {
                plugin.getUtils().pluginInfo(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("status".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.status")) {
                plugin.getUtils().pluginStatus(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("usage".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.usage")) {
                plugin.getUtils().usageCommand(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("test".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.test")) {
                plugin.getUtils().testPerms(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("load".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.load")) {
                plugin.getUtils().loadPlugin(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("unload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.unload")) {
                try {
                    plugin.getUtils().unloadPlugin(sender, args);
                } catch (Exception e) {
                    sender.sendMessage(Utilities.pre + Utilities.red + "Failed to unload plugin!");
                    plugin.getLog().log(Level.SEVERE, "{0} tried to unload a plugin and failed!{1}", new Object[]{sender.getName(), e});
                }
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("reload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.reload")) {
                plugin.getUtils().reloadPlugin(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("enable".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.enable")) {
                plugin.getUtils().enablePlugin(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else if ("disable".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.disable")) {
                plugin.getUtils().disablePlugin(sender, args);
            } else {
                plugin.getUtils().noPerms(sender);
            }
        } else {
            plugin.getUtils().helpList(sender);
        }
        return true;
    }
}