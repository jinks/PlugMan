package com.ryanclancy000.plugman;

import com.ryanclancy000.plugman.utilities.MetricsLite;
import com.ryanclancy000.plugman.utilities.Utilities;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {

    private List<String> aliases;
    private List<String> skipPlugins;
    //
    private final Utilities utils = new Utilities(this);
    
    @Override
    public void onDisable() {
        aliases.clear();
        skipPlugins.clear();
    }

    @Override
    public void onEnable() {
        loadConfig();
        registerCommands();
        startMetrics();
    }

    private void loadConfig() {
        try {
            this.getConfig().options().copyDefaults(true);
            aliases = this.getConfig().getStringList("command-aliases");
            skipPlugins = this.getConfig().getStringList("skip-on-reload");
        } catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Failed to load config - ignoring skip-plugins feature!{0}", e);
            skipPlugins = null;
        }
    }

    private void registerCommands() {
        this.getCommand("plugman").setExecutor(new PlugManCommands(this));
        this.getCommand("plugman").setAliases(aliases);
    }

    private void startMetrics() {
        if (this.getConfig().getBoolean("use-metrics")) {
            try {
                MetricsLite metrics = new MetricsLite(this);
                metrics.start();
                this.getLogger().log(Level.INFO, "Metrics successfully started!");
            } catch (Exception e) {
                this.getLogger().log(Level.SEVERE, "Failed to start Metrics!{0}", e);
            }
        } else {
            this.getLogger().log(Level.INFO, "Ignoring Metrics!");
        }
    }

    public List<String> getSkipped() {
        return this.skipPlugins;
    }
    
    public Utilities getUtils() {
        return this.utils;
    }
}