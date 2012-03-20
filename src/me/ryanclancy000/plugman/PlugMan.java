package me.ryanclancy000.plugman;

import java.io.IOException;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {
    
    private final PlugManCommands cHandler = new PlugManCommands(this);
    public PluginDescriptionFile PDF;
    List<String> aliases;
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEnable() {
        PDF = this.getDescription();
        getCommand("plugman").setExecutor(this);
        
        if (this.getConfig().getBoolean("use-pm-alias") == true) {
            aliases.add("pm");
            getCommand("plugman").setAliases(aliases);
        }
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().warning("Failed to enable Metrics tracking!");
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("plugman")) {
            return doCommand(sender, args);
        }
        
        return onCommand(sender, cmd, commandLabel, args);
    }
    
    private boolean doCommand(CommandSender sender, String[] args) {
        
        if (args.length == 0) {
            this.cHandler.thisInfo(sender);
            return true;
        }
        
        if ("help".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.help")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.helpList(sender);
            return true;
        }
        
        if ("list".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.list")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.listPlugins(sender, args);
            return true;
        }
        
        if ("vlist".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.vlist")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.vlistPlugins(sender, args);
            return true;
        }
        
        if ("info".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.info")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.pluginInfo(sender, args);
            return true;
        }
        
        if ("test".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.test")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.testPerms(sender, args);
            return true;
        }
        
        if ("purge".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.purge")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.purgePlugins(sender, args);
            return true;
        }
        
        if ("load".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.load")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.loadPlugin(sender, args);
            return true;
        }
        
        if ("reload".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.reload")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.reloadPlugin(sender, args);
            return true;
        }
        
        if ("enable".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.enable")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.enablePlugin(sender, args);
            return true;
        }
        
        if ("disable".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.disable")) {
                noPerms(sender);
                return true;
            }
            
            this.cHandler.disablePlugin(sender, args);
            return true;
        }
        
        return false;
    }
    
    public void noPerms(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You do not have permission for that command...");
    }
}
