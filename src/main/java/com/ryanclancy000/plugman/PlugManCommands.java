package com.ryanclancy000.plugman;

import com.ryanclancy000.plugman.utilities.Utilities;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlugManCommands
  implements CommandExecutor
{
  private final PlugMan plugin;

  public PlugManCommands(PlugMan instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (args.length == 0)
      this.plugin.util.thisInfo(sender);
    else if ("help".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.help"))
        this.plugin.util.helpList(sender);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("list".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.list"))
        this.plugin.util.listPlugins(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("vlist".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.vlist"))
        this.plugin.util.vlistPlugins(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("info".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.info"))
        this.plugin.util.pluginInfo(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("status".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.status"))
        this.plugin.util.pluginStatus(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("usage".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.usage"))
        this.plugin.util.usageCommand(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("test".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.test"))
        this.plugin.util.testPerms(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("load".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.load"))
        this.plugin.util.loadPlugin(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("unload".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.unload"))
        try {
          this.plugin.util.unloadPlugin(sender, args);
        } catch (Exception e) {
          sender.sendMessage(Utilities.pre + Utilities.red + "Failed to unload plugin!");
          PlugMan.logger.log(Level.SEVERE, "{0} tried to unload a plugin and failed!{1}", new Object[] { sender.getName(), e });
        }
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("reload".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.reload"))
        this.plugin.util.reloadPlugin(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("enable".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.enable"))
        this.plugin.util.enablePlugin(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else if ("disable".equalsIgnoreCase(args[0])) {
      if (sender.hasPermission("plugman.disable"))
        this.plugin.util.disablePlugin(sender, args);
      else
        this.plugin.util.noPerms(sender);
    }
    else {
      this.plugin.util.helpList(sender);
    }
    return true;
  }
}