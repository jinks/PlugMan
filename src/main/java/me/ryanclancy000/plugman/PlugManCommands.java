package me.ryanclancy000.plugman;

import me.ryanclancy000.plugman.utilities.Utilities;
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

        // PlugMan Info
        if (args.length == 0) {
            plugin.util.thisInfo(sender);
        }

        // Help Command
        if ("help".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.help")) {
                plugin.util.helpList(sender);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // List Command
        if ("list".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.list")) {
                plugin.util.listPlugins(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // vList Command
        if ("vlist".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.vlist")) {
                plugin.util.vlistPlugins(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Info Command
        if ("info".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.info")) {
                plugin.util.pluginInfo(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Status Command
        if ("status".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.status")) {
                plugin.util.pluginStatus(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Usage Command
        if ("usage".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.usage")) {
                plugin.util.usageCommand(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Test Command
        if ("test".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.test")) {
                plugin.util.testPerms(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Load Command
        if ("load".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.load")) {
                plugin.util.loadPlugin(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Unload Command
        if ("unload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.unload")) {
                try {
                    plugin.util.unloadPlugin(sender, args);
                } catch (Exception e) {
                    sender.sendMessage(Utilities.pre + Utilities.red + "Failed to unload plugin!");
                }
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Reload Command
        if ("reload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.reload")) {
                plugin.util.reloadPlugin(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Enable Command
        if ("enable".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.enable")) {
                plugin.util.enablePlugin(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // Disable Command
        if ("disable".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.disable")) {
                plugin.util.disablePlugin(sender, args);
            } else {
                plugin.util.noPerms(sender);
            }
        }

        // If args aren't valid, show help.
        plugin.util.helpList(sender);
        return true;
    }
}