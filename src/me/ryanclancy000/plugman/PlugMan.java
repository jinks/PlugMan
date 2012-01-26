package me.ryanclancy000.plugman;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {

    private final PlugManCommands cHandler = new PlugManCommands(this);
    Server server;
    PluginManager pm;
    CommandSender sender;
    ChatColor yellow = ChatColor.YELLOW;
    ChatColor green = ChatColor.GREEN;
    ChatColor red = ChatColor.RED;

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        server = this.getServer();
        pm = this.getServer().getPluginManager();
        getCommand("plugman").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("plugman")) {
            return doCommand(sender, args);
        }

        return onCommand(sender, cmd, commandLabel, args);
    }

    private boolean doCommand(CommandSender sender, String args[]) {

        if (args.length == 0) {
            return true;
        }
        
        if ("help".equalsIgnoreCase(args[0])) {
            cHandler.helpList();
            return true;
        }

        if ("load".equalsIgnoreCase(args[0])) {
            cHandler.loadPlugin();
            return true;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            cHandler.reloadPlugin();
            return true;
        }

        if ("enable".equalsIgnoreCase(args[0])) {
            cHandler.enablePlugin();
            return true;
        }

        if ("disable".equalsIgnoreCase(args[0])) {
            cHandler.disablePlugin();
            return true;
        }

        if ("info".equalsIgnoreCase(args[0])) {
            cHandler.pluginInfo();
            return true;
        }

        if ("usage".equalsIgnoreCase(args[0])) {
            cHandler.commandUsage();
            return true;
        }

        if ("describe".equalsIgnoreCase(args[0])) {
            cHandler.describeCommand();
            return true;
        }
        
        return false;

    }
}