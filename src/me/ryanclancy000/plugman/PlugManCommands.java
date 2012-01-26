package me.ryanclancy000.plugman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PlugManCommands {

    public PlugMan p;

    public PlugManCommands(PlugMan instance) {
        p = instance;
    }
    ChatColor yellow = ChatColor.YELLOW;
    ChatColor green = ChatColor.GREEN;
    ChatColor red = ChatColor.RED;
    ChatColor white = ChatColor.WHITE;

    public Plugin getPlugin(String parm) {
        for (Plugin plugin : p.getServer().getPluginManager().getPlugins()) {
            if (plugin.getDescription().getName().equalsIgnoreCase(parm)) {
                return plugin;
            }
        }
        return null;
    }

    public void thisInfo(CommandSender sender) {
        sender.sendMessage(yellow + p.PDF.getName() + green + " v" + p.PDF.getVersion() + white + " by " + green + p.PDF.getAuthors());
    }

    public void helpList(CommandSender sender) {
        sender.sendMessage(yellow + "PlugMan " + green + "Help:");
        sender.sendMessage(yellow + "/plugman " + green + "list - " + yellow + "Lists plugins.");
        sender.sendMessage(yellow + "/plugman " + green + "vlist - " + yellow + "Lists plugins with version.");
        sender.sendMessage(yellow + "/plugman " + green + "load (plugin) - " + yellow + "Loads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "reload (plugin) - " + yellow + "Reloads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "enable (plugin) - " + yellow + "Enables a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "disable (plugin) - " + yellow + "Disables a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "info (plugin) - " + yellow + "Shows version and other plugin info.");
        sender.sendMessage(yellow + "/plugman " + green + "usage (plugin) - " + yellow + "Lists commands registered by a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "describe (command) (plugin) - " + yellow + "Describe a command a plugin has registered.");
    }

    public void loadPlugin() {
    }

    public void reloadPlugin(CommandSender sender, String[] args) {
        
        if (args.length > 2) {
            sender.sendMessage(red + "Too many arguments!");
            return;
        }
        
        if (getPlugin(args[1]) != null) {
            Plugin targetPlugin = getPlugin(args[1]);
            Bukkit.getPluginManager().disablePlugin(targetPlugin);
            Bukkit.getPluginManager().enablePlugin(targetPlugin);
            sender.sendMessage(yellow + "[" + targetPlugin + "] " + green + "Reloaded!");
        } else {
            sender.sendMessage(red + "Plugin not found!");
        }
        
    }

    public void enablePlugin(CommandSender sender, String[] args) {
        
        if (args.length > 2 ) {
            sender.sendMessage(red + "Too many arguments!");
            return;
        }

        if (getPlugin(args[1]) != null) {
            Plugin targetPlugin = getPlugin(args[1]);
            Bukkit.getPluginManager().enablePlugin(targetPlugin);
            sender.sendMessage(yellow + "[" + targetPlugin + "] " + green + "Enabled!");
            return;
        } else {
            sender.sendMessage(red + "Plugin not found!");
        }
    }

    public void disablePlugin(CommandSender sender, String[] args) {
        
        if (args.length > 2 ) {
            sender.sendMessage(red + "Too many arguements!");
            return;
        }

        if (getPlugin(args[1]) != null) {
            Plugin targetPlugin = getPlugin(args[1]);
            Bukkit.getPluginManager().disablePlugin(targetPlugin);
            sender.sendMessage(yellow + "[" + targetPlugin + "] " + red + "Disabled!");
            return;
        } else {
            sender.sendMessage(red + "Plugin not found!");
        }
    }

    public void pluginInfo() {
    }

    public void commandUsage() {
    }

    public void describeCommand() {
    }
}