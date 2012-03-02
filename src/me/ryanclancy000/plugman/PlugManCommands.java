package me.ryanclancy000.plugman;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

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
        sender.sendMessage(yellow + "[" + p.PDF.getName() + "]" + green + " v" + p.PDF.getVersion() + white + " by " + green + p.PDF.getAuthors());
        sender.sendMessage(yellow + "- To view commands, do /plugman " + green + "help");
    }

    public void helpList(CommandSender sender) {
        sender.sendMessage(yellow + "PlugMan " + green + "Help:");
        sender.sendMessage(yellow + "/plugman " + green + "list - " + yellow + "Lists plugins.");
        sender.sendMessage(yellow + "/plugman " + green + "info (plugin) - " + yellow + "Gives plugin info.");
        sender.sendMessage(yellow + "/plugman " + green + "load (plugin) - " + yellow + "Loads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "reload (plugin) - " + yellow + "Reloads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "enable (plugin) - " + yellow + "Enables a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "disable (plugin) - " + yellow + "Disables a plugin.");
    }

    public void listPlugins(CommandSender sender, String[] args) {

        if (args.length > 1) {
            sender.sendMessage(red + "Too many arguments");
            return;
        }
        StringBuilder pluginList = new StringBuilder();
        for (Plugin pl : p.getServer().getPluginManager().getPlugins()) {
            if (pluginList.length() > 0) {
                pluginList.append(white + ", ");
            }
            pluginList.append(pl.isEnabled() ? green : red);
            pluginList.append(pl.getDescription().getFullName());
        }
        sender.sendMessage(yellow + "Plugins: " + pluginList.toString());
    }

    public void thisInfo(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(red + "Must specify a plugin!");
            return;
        }

        if (args.length > 2) {
            sender.sendMessage(red + "Too many arguments");
            return;
        }

        Plugin targetPlugin = getPlugin(args[1]);
        sender.sendMessage(green + targetPlugin.getDescription().getFullName() + white + " by " + green + targetPlugin.getDescription().getAuthors());

    }

    public void loadPlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(red + "Must specify a plugin!");
            return;
        }

        if (args.length > 2) {
            sender.sendMessage(red + "Too many arguments!");
            return;
        }
        
        if (getPlugin(args[1]) == null) {
            return;
        }

        String pluginName = args[1];
        Plugin targetPlugin = getPlugin(args[1]);
        File pluginFile = new File(new File("plugins"), pluginName + ".jar");

        if (targetPlugin != null) {
            if (targetPlugin.isEnabled()) {
                sender.sendMessage(red + "Plugin already loaded and is enabled!");
                return;
            } else {
                sender.sendMessage(red + "Plugin already loaded, but is disabled!");
                return;
            }
        }

        if (pluginFile.isFile()) {

            try {
                Bukkit.getPluginManager().loadPlugin(pluginFile);
                Bukkit.getPluginManager().enablePlugin(getPlugin(args[1]));
                sender.sendMessage(yellow + "[" + getPlugin(args[1]) + "] " + green + "Loaded and Enabled!");
            } catch (UnknownDependencyException e) {
                sender.sendMessage(red + "File exists, but is not a plugin file.");
            } catch (InvalidPluginException e) {
                sender.sendMessage(red + "File exists, but is not a plugin file.");
            } catch (InvalidDescriptionException e) {
                sender.sendMessage(red + "Plugin exists, but is invalid.");
            }

        } else {
            sender.sendMessage(red + "File doesn't exist!");
        }

    }

    public void reloadPlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(red + "Must specify a plugin!");
            return;
        }

        if (args.length > 2) {
            sender.sendMessage(red + "Too many arguments!");
            return;
        }

        if (getPlugin(args[1]) == null) {
            sender.sendMessage(red + "Plugin not found!");
            return;
        }

        if ("all".equalsIgnoreCase(args[1])) {
            for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                Bukkit.getPluginManager().disablePlugin(p);
                Bukkit.getPluginManager().enablePlugin(p);
            }
            sender.sendMessage(yellow + "[PlugMan] " + green + "All plugins reloaded!");
            return;
        }

        Plugin targetPlugin = getPlugin(args[1]);
        Bukkit.getPluginManager().disablePlugin(targetPlugin);
        Bukkit.getPluginManager().enablePlugin(targetPlugin);
        sender.sendMessage(yellow + "[" + targetPlugin + "] " + green + "Reloaded!");

    }

    public void enablePlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(red + "Must specify a plugin!");
            return;
        }

        if (args.length > 2) {
            sender.sendMessage(red + "Too many arguments!");
            return;
        }

        if (getPlugin(args[1]).isEnabled()) {
            sender.sendMessage(red + "Plugin already enabled!");
            return;
        }

        if (getPlugin(args[1]) == null) {
            return;
        }

        Plugin targetPlugin = getPlugin(args[1]);
        Bukkit.getPluginManager().enablePlugin(targetPlugin);
        sender.sendMessage(yellow + "[" + targetPlugin + "] " + green + "Enabled!");
        sender.sendMessage(red + "Plugin not found!");

    }

    public void disablePlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(red + "Must specify a plugin!");
            return;
        }

        if (args.length > 2) {
            sender.sendMessage(red + "Too many arguements!");
            return;
        }

        if (!getPlugin(args[1]).isEnabled()) {
            sender.sendMessage(red + "Plugin already disabled!");
            return;
        }

        if (getPlugin(args[1]) == null) {
            return;
        }

        Plugin targetPlugin = getPlugin(args[1]);
        Bukkit.getPluginManager().disablePlugin(targetPlugin);
        sender.sendMessage(yellow + "[" + targetPlugin + "] " + red + "Disabled!");
        sender.sendMessage(red + "Plugin not found!");

    }
}