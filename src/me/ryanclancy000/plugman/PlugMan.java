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
    public Metrics metrics;
    public List skipPlugins;

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        PDF = this.getDescription();
        getCommand("plugman").setExecutor(this);
        metrics.start();
        loadConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("plugman")) {
            try {
                return doCommand(sender, args);
            } catch (Exception e) {
            }
        }
        return onCommand(sender, cmd, commandLabel, args);
    }

    private boolean doCommand(CommandSender sender, String[] args) throws NoSuchFieldException, IllegalAccessException {

        // PlugMan Info
        if (args.length == 0) {
            this.cHandler.thisInfo(sender);
            return true;
        }

        // Help Command
        if ("help".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.help")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.helpList(sender);
            return true;
        }

        // List Command
        if ("list".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.list")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.listPlugins(sender, args);
            return true;
        }

        // vList Command
        if ("vlist".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.vlist")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.vlistPlugins(sender, args);
            return true;
        }

        // Info Command
        if ("info".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.info")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.pluginInfo(sender, args);
            return true;
        }

        // Usage Command
        if ("usage".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.usage")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.usageCommand(sender, args);
            return true;
        }

        // Test Command
        if ("test".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.test")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.testPerms(sender, args);
            return true;
        }

        // Purge Command
        if ("purge".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.purge")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.purgePlugins(sender, args);
            return true;
        }

        // Load Command
        if ("load".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.load")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.loadPlugin(sender, args);
            return true;
        }

        // Unload Command
        if ("unload".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.unload")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.unloadPlugin(sender, args);
            return true;
        }

        // Reload Command
        if ("reload".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.reload")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.reloadPlugin(sender, args);
            return true;
        }

        // Enable Command
        if ("enable".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("plugman.enable")) {
                noPerms(sender);
                return true;
            }
            this.cHandler.enablePlugin(sender, args);
            return true;
        }

        // Disable Command
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

    public void loadConfig() {
        try {
            this.getConfig().options().copyDefaults(true);
            skipPlugins = this.getConfig().getList("skip-on-reload");
            this.saveConfig();
        } catch (Exception e) {
            log("Failed to load config, disabling PlugMan");
        }
    }
    
    public void log(String s) {
        this.getLogger().info(s);
    }
}
