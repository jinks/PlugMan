package me.ryanclancy000.plugman;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;

public class PlugManCommands {

    public PlugMan plugin;
    public ChatColor red = ChatColor.RED;
    public ChatColor white = ChatColor.WHITE;
    public ChatColor green = ChatColor.GREEN;
    public ChatColor yellow = ChatColor.YELLOW;
    public String pre = yellow + "[PlugMan] ";

    public PlugManCommands(PlugMan instance) {
        this.plugin = instance;
    }

    public Plugin getPlugin(String parm) {
        for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (pl.getDescription().getName().equalsIgnoreCase(parm)) {
                return pl;
            }
        }
        return null;
    }

    // PlugMan Command
    public void thisInfo(CommandSender sender) {
        sender.sendMessage(pre + green + "v" + plugin.PDF.getVersion() + yellow + " by " + green + "ryanclancy000");
        sender.sendMessage(yellow + "- To view commands, do /plugman " + green + "help");
    }

    // Help Command
    public void helpList(CommandSender sender) {
        sender.sendMessage(pre + green + "Help:");
        sender.sendMessage(yellow + "/plugman " + green + "list - " + yellow + "Lists plugins.");
        sender.sendMessage(yellow + "/plugman " + green + "vlist - " + yellow + "Lists plugins with versions.");
        sender.sendMessage(yellow + "/plugman " + green + "info [plugin] - " + yellow + "Gives plugin info.");
        sender.sendMessage(yellow + "/plugman " + green + "test [permission] [player] - " + yellow + "Test permission node.");
        sender.sendMessage(yellow + "/plugman " + green + "purge - " + yellow + "Disables and removes all plugins - careful.");
        sender.sendMessage(yellow + "/plugman " + green + "load [plugin] - " + yellow + "Loads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "unload [plugin] - " + yellow + "Unloads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "reload [plugin|all] - " + yellow + "Reloads a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "enable [plugin|all] - " + yellow + "Enables a plugin.");
        sender.sendMessage(yellow + "/plugman " + green + "disable [plugin|all] - " + yellow + "Disables a plugin.");
    }

    // List Command
    public void listPlugins(CommandSender sender, String[] args) {

        if (args.length > 1) {
            sender.sendMessage(pre + red + "Too many arguments");
            return;
        }

        StringBuilder pluginList = new StringBuilder();

        for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (pluginList.length() > 0) {
                pluginList.append(white + ", ");
            }
            pluginList.append(pl.isEnabled() ? green : red);
            pluginList.append(pl.getDescription().getName());
        }
        sender.sendMessage(yellow + "Plugins: " + pluginList);

    }

    // vList Command
    public void vlistPlugins(CommandSender sender, String[] args) {

        if (args.length > 1) {
            sender.sendMessage(pre + red + "Too many arguments");
            return;
        }

        StringBuilder pluginList = new StringBuilder();

        for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (pluginList.length() > 0) {
                pluginList.append(white + ", ");
            }
            pluginList.append(pl.isEnabled() ? green : red);
            pluginList.append(pl.getDescription().getFullName());
        }
        sender.sendMessage(yellow + "Plugins: " + pluginList);

    }

    // Info Command
    public void pluginInfo(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        Plugin targetPlugin = getPlugin(pl);

        if (targetPlugin == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }

        sender.sendMessage(green + targetPlugin.getDescription().getFullName() + white + " by " + green + targetPlugin.getDescription().getAuthors());

    }
    
    // Status Command
    public void pluginStatus(CommandSender sender, String[] args) {
                
        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }
        
        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }
        
        Plugin targetPlugin = getPlugin(pl);
        
        if (targetPlugin == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }
        
        if (targetPlugin.isEnabled()) {
            sender.sendMessage(pre + green + targetPlugin.getName() + " is enabled!");
            return;
        } else {
            sender.sendMessage(pre + green + targetPlugin.getName() + " is disabled!");
        }
        
    }

    // Usage Command
    public void usageCommand(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        Plugin targetPlugin = getPlugin(pl);

        if (targetPlugin == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }

        ArrayList<String> out = new ArrayList<String>();
        ArrayList<String> parsedCommands = new ArrayList<String>();
        Map commands = targetPlugin.getDescription().getCommands();

        if (commands != null) {
            Iterator commandsIt = commands.entrySet().iterator();
            while (commandsIt.hasNext()) {
                Entry thisEntry = (Entry) commandsIt.next();
                if (thisEntry != null) {
                    parsedCommands.add((String) thisEntry.getKey());
                }
            }
        }

        if (!parsedCommands.isEmpty()) {

            StringBuilder commandsOut = new StringBuilder();
            commandsOut.append(pre).append(green + "Command List: ");

            for (int i = 0; i < parsedCommands.size(); i++) {

                String thisCommand = parsedCommands.get(i);

                if (commandsOut.length() + thisCommand.length() > 55) {
                    sender.sendMessage(commandsOut.toString());
                    commandsOut = new StringBuilder();
                }

                if (parsedCommands.size() > 0) {
                    commandsOut.append(yellow + "\"").append(thisCommand).append("\" ");
                } else {
                    commandsOut.append(yellow + "\"").append(thisCommand).append("\"");
                }

            }

            out.add(commandsOut.toString());

        } else {
            out.add(pre + red + "Plugin has no registered commands!");
        }

        for (String s : out) {
            sender.sendMessage(s);
        }

    }

    // Test Command
    public void testPerms(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify permission and player!");
            return;
        }

        if (args.length == 2) {
            if (sender.hasPermission(args[1])) {
                sender.sendMessage(pre + green + "You have permission for " + args[1]);
            } else {
                sender.sendMessage(pre + red + "You do not have permission for " + args[1]);
            }
        }

        if (args.length == 3) {

            try {
                Player target = Bukkit.getPlayer(args[2]);
                if (target.isOnline()) {
                    if (target.hasPermission(args[1])) {
                        sender.sendMessage(pre + green + target.getName() + " has permission for " + args[1]);
                    } else {
                        sender.sendMessage(pre + red + target.getName() + " does not have permission for " + args[1]);
                    }
                }

            } catch (Exception e) {
                sender.sendMessage(pre + red + "Player not online!");
            }
        }


    }

    // Purge Command
    public void purgePlugins(CommandSender sender, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(pre + red + "Too many arguments!");
            return;
        }

        Bukkit.getPluginManager().clearPlugins();
        sender.sendMessage(pre + red + "All plugins disabled, reload (from console) or restart the server to re-enable them!");

    }

    // Load Command
    public void loadPlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        Plugin targetPlugin = getPlugin(pl);
        File pluginFile = new File(new File("plugins"), pl + ".jar");

        if (targetPlugin != null) {
            if (targetPlugin.isEnabled()) {
                sender.sendMessage(pre + red + "Plugin already loaded and is enabled!");
                return;
            }
            sender.sendMessage(pre + red + "Plugin already loaded, but is disabled!");
            return;
        }

        if (pluginFile.isFile()) {
            try {
                Bukkit.getPluginManager().loadPlugin(pluginFile);
                Bukkit.getPluginManager().enablePlugin(getPlugin(pl));
                sender.sendMessage(pre + green + getPlugin(pl) + " Loaded and Enabled!");
            } catch (UnknownDependencyException e) {
                sender.sendMessage(pre + red + "File exists, but is not a plugin file.");
            } catch (InvalidPluginException e) {
                sender.sendMessage(pre + red + "File exists, but is not a plugin file.");
            } catch (InvalidDescriptionException e) {
                sender.sendMessage(pre + red + "Plugin exists, but is invalid.");
            }
        } else {
            sender.sendMessage(pre + red + "File doesn't exist!");
        }

    }

    // Unload Command
    public void unloadPlugin(CommandSender sender, String[] args) throws NoSuchFieldException, IllegalAccessException {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        if (getPlugin(pl) == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }

        PluginManager pm = Bukkit.getServer().getPluginManager();
        SimplePluginManager spm = (SimplePluginManager) pm;
        SimpleCommandMap cmdMap = null;
        List<Plugin> plugins = null;
        Map<String, Plugin> names = null;
        Map<String, Command> commands = null;
        Map<Event, SortedSet<RegisteredListener>> listeners = null;
        boolean reloadlisteners = true;

        if (spm != null) {
            Field pluginsField = spm.getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            plugins = (List<Plugin>) pluginsField.get(spm);

            Field lookupNamesField = spm.getClass().getDeclaredField("lookupNames");
            lookupNamesField.setAccessible(true);
            names = (Map<String, Plugin>) lookupNamesField.get(spm);

            try {
                Field listenersField = spm.getClass().getDeclaredField("listeners");
                listenersField.setAccessible(true);
                listeners = (Map<Event, SortedSet<RegisteredListener>>) listenersField.get(spm);
            } catch (Exception e) {
                reloadlisteners = false;
            }

            Field commandMapField = spm.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            cmdMap = (SimpleCommandMap) commandMapField.get(spm);

            Field knownCommandsField = cmdMap.getClass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            commands = (Map<String, Command>) knownCommandsField.get(cmdMap);
        }

        for (Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (p.getDescription().getName().equalsIgnoreCase(pl)) {
                pm.disablePlugin(p);
                sender.sendMessage(pre + green + p.getName() + " has been unloaded and disabled!");
                if (plugins != null && plugins.contains(p)) {
                    plugins.remove(p);
                }

                if (names != null && names.containsKey(pl)) {
                    names.remove(pl);
                }

                if (listeners != null && reloadlisteners) {
                    for (SortedSet<RegisteredListener> set : listeners.values()) {
                        for (Iterator<RegisteredListener> it = set.iterator(); it.hasNext();) {
                            RegisteredListener value = it.next();

                            if (value.getPlugin() == p) {
                                it.remove();
                            }
                        }
                    }
                }

                if (cmdMap != null) {
                    for (Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator(); it.hasNext();) {
                        Map.Entry<String, Command> entry = it.next();
                        if (entry.getValue() instanceof PluginCommand) {
                            PluginCommand c = (PluginCommand) entry.getValue();
                            if (c.getPlugin() == p) {
                                c.unregister(cmdMap);
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
    }

    // Reload Command
    public void reloadPlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        if ("all".equalsIgnoreCase(args[1]) || "*".equalsIgnoreCase(args[1])) {
            for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
                if (!(plugin.skipPlugins.contains(pl.getDescription().getName()))) {
                    Bukkit.getPluginManager().disablePlugin(pl);
                    Bukkit.getPluginManager().enablePlugin(pl);
                }
            }
            sender.sendMessage(pre + green + "All plugins reloaded!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        if (getPlugin(pl) == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }

        Plugin targetPlugin = getPlugin(pl);
        Bukkit.getPluginManager().disablePlugin(targetPlugin);
        Bukkit.getPluginManager().enablePlugin(targetPlugin);
        sender.sendMessage(pre + green + targetPlugin.getName() + " Reloaded!");

    }

    // Enable Command
    public void enablePlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        if ("all".equalsIgnoreCase(args[1]) || "*".equalsIgnoreCase(args[1])) {
            for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
                Bukkit.getPluginManager().enablePlugin(pl);
            }
            sender.sendMessage(pre + green + "All plugins enabled!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        if (getPlugin(pl) == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }

        if (getPlugin(pl).isEnabled()) {
            sender.sendMessage(pre + red + "Plugin already enabled!");
            return;
        }

        Plugin targetPlugin = getPlugin(pl);
        Bukkit.getPluginManager().enablePlugin(targetPlugin);
        sender.sendMessage(pre + green + targetPlugin.getName() + " Enabled!");

    }

    // Disable Command
    public void disablePlugin(CommandSender sender, String[] args) {

        if (args.length == 1) {
            sender.sendMessage(pre + red + "Must specify a plugin!");
            return;
        }

        if ("all".equalsIgnoreCase(args[1]) || "*".equalsIgnoreCase(args[1])) {
            for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
                Bukkit.getPluginManager().disablePlugin(pl);
            }
            sender.sendMessage(pre + red + "All plugins disabled!");
            return;
        }

        String pl = args[1];
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                pl = pl + " " + args[i];
            }
        }

        if (getPlugin(pl) == null) {
            sender.sendMessage(pre + red + "Plugin not found!");
            return;
        }

        if (!getPlugin(pl).isEnabled()) {
            sender.sendMessage(pre + red + "Plugin already disabled!");
            return;
        }

        Plugin targetPlugin = getPlugin(pl);
        Bukkit.getPluginManager().disablePlugin(targetPlugin);
        sender.sendMessage(pre + red + targetPlugin.getName() + " Disabled!");

    }
}