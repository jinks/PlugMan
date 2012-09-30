package com.ryanclancy000.plugman.utilities;

import com.ryanclancy000.plugman.PlugMan;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;

public class Utilities
{
  private final PlugMan plugin;
  public static final ChatColor red = ChatColor.RED;
  public static final ChatColor gray = ChatColor.GRAY;
  public static final ChatColor white = ChatColor.WHITE;
  public static final ChatColor green = ChatColor.GREEN;
  public static final ChatColor yellow = ChatColor.YELLOW;
  public static final String pre = gray + "[" + green + "PlugMan" + gray + "] ";
  private static final String tooMany = red + "Too many arguments!";
  private static final String specifyPlugin = red + "Must specify a plugin!";
  private static final String pluginNotFound = red + "Plugin not found!";

  public Utilities(PlugMan plugin) {
    this.plugin = plugin;
  }

  private Plugin getPlugin(String plugin) {
    for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
      if (pl.getDescription().getName().equalsIgnoreCase(plugin)) {
        return pl;
      }
    }
    return null;
  }

  private String consolidateArgs(String[] args) {
    String pl = args[1];
    if (args.length > 2) {
      for (int i = 2; i < args.length; i++) {
        pl = pl + " " + args[i];
      }
    }
    return pl;
  }

  public void thisInfo(CommandSender sender)
  {
    sender.sendMessage(pre + green + "v" + this.plugin.getDescription().getVersion() + gray + " by " + green + "ryanclancy000");
    sender.sendMessage(gray + "-" + green + " To view commands, do /plugman help");
  }

  public void helpList(CommandSender sender)
  {
    sender.sendMessage(white + "--------------------- " + gray + "[" + green + " PlugMan " + gray + "]" + white + " ---------------------");
    sender.sendMessage(green + "/plugman list" + white + " - " + gray + "List all plugins.");
    sender.sendMessage(green + "/plugman vlist" + white + " - " + gray + "List all plugins with versions.");
    sender.sendMessage(green + "/plugman info [plugin]" + white + " - " + gray + "Give plugin info.");
    sender.sendMessage(green + "/plugman status [plugin]" + white + " - " + gray + "Give the status of a plugin.");
    sender.sendMessage(green + "/plugman test [permission] [player]" + white + " - " + gray + "Test a permission node.");
    sender.sendMessage(green + "/plugman load [plugin]" + white + " - " + gray + "Load a plugin.");
    sender.sendMessage(green + "/plugman unload [plugin]" + white + " - " + gray + "Unload a plugin.");
    sender.sendMessage(green + "/plugman reload [plugin|all]" + white + " - " + gray + "Reload a plugin.");
    sender.sendMessage(green + "/plugman enable [plugin|all]" + white + " - " + gray + "Enable a plugin.");
    sender.sendMessage(green + "/plugman disable [plugin|all]" + white + " - " + gray + "Disable a plugin.");
  }

  public void listPlugins(CommandSender sender, String[] args)
  {
    if (args.length > 1) {
      sender.sendMessage(pre + red + tooMany);
      return;
    }

    StringBuilder list = new StringBuilder();
    List pluginList = new ArrayList();

    for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
      String plName = "";
      plName = plName + (pl.isEnabled() ? green : red);
      plName = plName + pl.getDescription().getName();
      pluginList.add(plName);
    }
    Collections.sort(pluginList, String.CASE_INSENSITIVE_ORDER);
    for (String plName : pluginList) {
      if (list.length() > 0) {
        list.append(white + ", ");
      }
      list.append(plName);
    }
    sender.sendMessage(pre + gray + "Plugins: " + list);
  }

  public void vlistPlugins(CommandSender sender, String[] args)
  {
    if (args.length > 1) {
      sender.sendMessage(pre + red + tooMany);
      return;
    }

    StringBuilder list = new StringBuilder();
    List pluginList = new ArrayList();

    for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
      String plName = "";
      plName = plName + (pl.isEnabled() ? green : red);
      plName = plName + pl.getDescription().getFullName();
      pluginList.add(plName);
    }
    Collections.sort(pluginList, String.CASE_INSENSITIVE_ORDER);
    for (String plName : pluginList) {
      if (list.length() > 0) {
        list.append(white + ", ");
      }
      list.append(plName);
    }
    sender.sendMessage(pre + gray + "Plugins: " + list);
  }

  public void pluginInfo(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    Plugin targetPlugin = getPlugin(consolidateArgs(args));

    if (targetPlugin == null) {
      sender.sendMessage(pre + red + pluginNotFound);
      return;
    }

    sender.sendMessage(pre + gray + "Plugin Info: " + green + targetPlugin.getName());
    sender.sendMessage(green + "Version: " + gray + targetPlugin.getDescription().getVersion());
    sender.sendMessage(green + "Authors: " + gray + targetPlugin.getDescription().getAuthors());
    sender.sendMessage(green + "Status: " + (targetPlugin.isEnabled() ? green + "Enabled" : new StringBuilder().append(red).append("Disabled").toString()));
  }

  public void pluginStatus(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    Plugin targetPlugin = getPlugin(consolidateArgs(args));

    if (targetPlugin == null) {
      sender.sendMessage(pre + red + pluginNotFound);
      return;
    }

    if (targetPlugin.isEnabled())
      sender.sendMessage(pre + green + targetPlugin.getName() + " is enabled!");
    else
      sender.sendMessage(pre + green + targetPlugin.getName() + " is disabled!");
  }

  public void usageCommand(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    Plugin targetPlugin = getPlugin(consolidateArgs(args));

    if (targetPlugin == null) {
      sender.sendMessage(pre + red + pluginNotFound);
      return;
    }

    ArrayList out = new ArrayList();
    ArrayList parsedCommands = new ArrayList();
    Map commands = targetPlugin.getDescription().getCommands();

    if (commands != null) {
      Iterator commandsIt = commands.entrySet().iterator();
      while (commandsIt.hasNext()) {
        Map.Entry thisEntry = (Map.Entry)commandsIt.next();
        if (thisEntry != null) {
          parsedCommands.add((String)thisEntry.getKey());
        }
      }
    }

    if (!parsedCommands.isEmpty())
    {
      StringBuilder commandsOut = new StringBuilder();
      commandsOut.append(pre).append(gray + "Command List: ");

      for (int i = 0; i < parsedCommands.size(); i++)
      {
        String thisCommand = (String)parsedCommands.get(i);

        if (commandsOut.length() + thisCommand.length() > 55) {
          sender.sendMessage(commandsOut.toString());
          commandsOut = new StringBuilder();
        }

        if (parsedCommands.size() > 0)
          commandsOut.append(green + "\"").append(thisCommand).append("\" ");
        else {
          commandsOut.append(green + "\"").append(thisCommand).append("\"");
        }

      }

      out.add(commandsOut.toString());
    }
    else {
      out.add(pre + red + "Plugin has no registered commands!");
    }

    for (String s : out)
      sender.sendMessage(s);
  }

  public void testPerms(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + "Must specify permission and player!");
      return;
    }

    if (args.length == 2) {
      if (sender.hasPermission(args[1]))
        sender.sendMessage(pre + green + "You have permission for '" + args[1] + "'.");
      else {
        sender.sendMessage(pre + red + "You do not have permission for '" + args[1] + "'.");
      }
    }

    if (args.length == 3) {
      Player target = Bukkit.getPlayer(args[2]);
      if (target == null) {
        sender.sendMessage(pre + red + "Player not found!");
      }
      else if (target.hasPermission(args[1]))
        sender.sendMessage(pre + green + target.getName() + " has permission for " + args[1]);
      else
        sender.sendMessage(pre + red + target.getName() + " does not have permission for " + args[1]);
    }
  }

  public void loadPlugin(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    String pl = consolidateArgs(args);
    Plugin targetPlugin = getPlugin(pl);
    File pluginFile = new File(new File("plugins"), pl + ".jar");

    if (targetPlugin != null) {
      if (targetPlugin.isEnabled()) {
        sender.sendMessage(pre + red + "Plugin already loaded and enabled!");
        return;
      }
      sender.sendMessage(pre + red + "Plugin already loaded, but is disabled!");
      return;
    }

    if (pluginFile.isFile())
      try {
        this.plugin.getServer().getPluginManager().loadPlugin(pluginFile);
        targetPlugin = getPlugin(pl);
        this.plugin.getServer().getPluginManager().enablePlugin(targetPlugin);
        sender.sendMessage(pre + green + getPlugin(pl) + " loaded and enabled!");
      } catch (UnknownDependencyException e) {
        sender.sendMessage(pre + red + "File exists, but is missing a dependency!");
      } catch (InvalidPluginException e) {
        sender.sendMessage(pre + red + "File exists, but isn't a plugin file!");
      } catch (InvalidDescriptionException e) {
        sender.sendMessage(pre + red + "Plugin exists, but has an invalid description!");
      }
    else
      sender.sendMessage(pre + red + "File doesn't exist!");
  }

  public void unloadPlugin(CommandSender sender, String[] args)
    throws NoSuchFieldException, IllegalAccessException
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    String pl = consolidateArgs(args);

    if (getPlugin(pl) == null) {
      sender.sendMessage(pre + red + pluginNotFound);
      return;
    }

    PluginManager pm = Bukkit.getServer().getPluginManager();
    SimplePluginManager spm = (SimplePluginManager)pm;
    SimpleCommandMap cmdMap = null;
    List plugins = null;
    Map names = null;
    Map commands = null;
    Map listeners = null;
    boolean reloadlisteners = true;

    if (spm != null) {
      Field pluginsField = spm.getClass().getDeclaredField("plugins");
      pluginsField.setAccessible(true);
      plugins = (List)pluginsField.get(spm);

      Field lookupNamesField = spm.getClass().getDeclaredField("lookupNames");
      lookupNamesField.setAccessible(true);
      names = (Map)lookupNamesField.get(spm);
      try
      {
        Field listenersField = spm.getClass().getDeclaredField("listeners");
        listenersField.setAccessible(true);
        listeners = (Map)listenersField.get(spm);
      } catch (Exception e) {
        reloadlisteners = false;
      }

      Field commandMapField = spm.getClass().getDeclaredField("commandMap");
      commandMapField.setAccessible(true);
      cmdMap = (SimpleCommandMap)commandMapField.get(spm);

      Field knownCommandsField = cmdMap.getClass().getDeclaredField("knownCommands");
      knownCommandsField.setAccessible(true);
      commands = (Map)knownCommandsField.get(cmdMap);
    }
    Plugin p;
    Iterator it;
    for (p : Bukkit.getServer().getPluginManager().getPlugins())
      if (p.getDescription().getName().equalsIgnoreCase(pl)) {
        pm.disablePlugin(p);
        sender.sendMessage(pre + green + p.getName() + " has been unloaded and disabled!");
        if ((plugins != null) && (plugins.contains(p))) {
          plugins.remove(p);
        }

        if ((names != null) && (names.containsKey(pl))) {
          names.remove(pl);
        }

        if ((listeners != null) && (reloadlisteners))
          for (SortedSet set : listeners.values())
            for (it = set.iterator(); it.hasNext(); ) {
              RegisteredListener value = (RegisteredListener)it.next();

              if (value.getPlugin() == p)
                it.remove();
            }
        Iterator it;
        if (cmdMap != null)
          for (it = commands.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            if ((entry.getValue() instanceof PluginCommand)) {
              PluginCommand c = (PluginCommand)entry.getValue();
              if (c.getPlugin() == p) {
                c.unregister(cmdMap);
                it.remove();
              }
            }
          }
      }
  }

  public void reloadPlugin(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    if (("all".equalsIgnoreCase(args[1])) || ("*".equalsIgnoreCase(args[1]))) {
      for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
        if ((this.plugin.skipPlugins.contains(pl.getName())) || (this.plugin.skipPlugins == null)) {
          return;
        }
        Bukkit.getPluginManager().disablePlugin(pl);
        Bukkit.getPluginManager().enablePlugin(pl);
      }

      sender.sendMessage(pre + green + "All plugins reloaded!");
      return;
    }

    String pl = consolidateArgs(args);

    if (getPlugin(pl) == null) {
      sender.sendMessage(pre + red + pluginNotFound);
      return;
    }

    Plugin targetPlugin = getPlugin(pl);
    Bukkit.getPluginManager().disablePlugin(targetPlugin);
    Bukkit.getPluginManager().enablePlugin(targetPlugin);
    sender.sendMessage(pre + green + targetPlugin.getName() + " Reloaded!");
  }

  public void enablePlugin(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    if (("all".equalsIgnoreCase(args[1])) || ("*".equalsIgnoreCase(args[1]))) {
      for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
        Bukkit.getPluginManager().enablePlugin(pl);
      }
      sender.sendMessage(pre + green + "All plugins enabled!");
      return;
    }

    String pl = consolidateArgs(args);

    if (getPlugin(pl) == null) {
      sender.sendMessage(pre + red + pluginNotFound);
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

  public void disablePlugin(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      sender.sendMessage(pre + red + specifyPlugin);
      return;
    }

    if (("all".equalsIgnoreCase(args[1])) || ("*".equalsIgnoreCase(args[1]))) {
      for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
        Bukkit.getPluginManager().disablePlugin(pl);
      }
      sender.sendMessage(pre + red + "All plugins disabled!");
      return;
    }

    String pl = consolidateArgs(args);

    if (getPlugin(pl) == null) {
      sender.sendMessage(pre + red + pluginNotFound);
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

  public void noPerms(CommandSender sender)
  {
    sender.sendMessage(red + "You do not have permission for that command...");
  }
}