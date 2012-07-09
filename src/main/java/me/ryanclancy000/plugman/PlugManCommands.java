package me.ryanclancy000.plugman;

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
            return true;
        }

        // Help Command
        if ("help".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.help")) {
                plugin.util.helpList(sender);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // List Command
        if ("list".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.list")) {
                plugin.util.listPlugins(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // vList Command
        if ("vlist".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.vlist")) {
                plugin.util.vlistPlugins(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Info Command
        if ("info".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.info")) {
                plugin.util.pluginInfo(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Status Command
        if ("status".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.status")) {
                plugin.util.pluginStatus(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Usage Command
        if ("usage".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.usage")) {
                plugin.util.usageCommand(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Test Command
        if ("test".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.test")) {
                plugin.util.testPerms(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Load Command
        if ("load".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.load")) {
                plugin.util.loadPlugin(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Unload Command
        if ("unload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.unload")) {
                try {
                    plugin.util.unloadPlugin(sender, args);
                } catch (NoSuchFieldException ex) {
                    sender.sendMessage(plugin.util.pre + plugin.util.red + "Failed to unload plugin!");
                } catch (IllegalAccessException ex) {
                    sender.sendMessage(plugin.util.pre + plugin.util.red + "Failed to unload plugin!");
                }
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Reload Command
        if ("reload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.reload")) {
                plugin.util.reloadPlugin(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Enable Command
        if ("enable".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.enable")) {
                plugin.util.enablePlugin(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }

        // Disable Command
        if ("disable".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("plugman.disable")) {
                plugin.util.disablePlugin(sender, args);
                return true;
            }
            plugin.util.noPerms(sender);
            return true;
        }
        
        // If args aren't valid, show help.
        plugin.util.helpList(sender);
        return true;
    }
}