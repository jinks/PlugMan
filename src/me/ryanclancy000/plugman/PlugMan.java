package me.ryanclancy000.plugman;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {
    
    private final PlugManCommands cHandler = new PlugManCommands(this);

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return true;
    }
    
    public void doCommand(CommandSender sender, String[] args) {
        
    }
    
}