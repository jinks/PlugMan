package me.ryanclancy000.plugman;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PlugManCommands {

    public PlugMan p;

    public PlugManCommands(PlugMan instance) {
        p = instance;
    }
    ChatColor yellow = ChatColor.YELLOW;
    ChatColor green = ChatColor.GREEN;
    ChatColor red = ChatColor.RED;
    ChatColor white = ChatColor.WHITE;

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

    public void reloadPlugin() {
    }

    public void enablePlugin(CommandSender sender, String[] args) {
    }

    public void disablePlugin() {
    }

    public void pluginInfo() {
    }

    public void commandUsage() {
    }

    public void describeCommand() {
    }
}