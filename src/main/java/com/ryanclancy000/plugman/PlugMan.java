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
    private final Utilities util = new Utilities(this);
    private final PlugManCommands cHandler = new PlugManCommands(this);

    @Override
    public void onEnable() {
        loadConfig();
        registerCommands();
        startMetrics();
    }

    private void loadConfig() {
        try {
            aliases = this.getConfig().getStringList("aliases");
            skipPlugins = this.getConfig().getStringList("skip-on-reload");
        } catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Failed to load config - ignoring skip-plugins feature!{0}", e);
            skipPlugins = null;
        }
    }
    
    private void registerCommands() {
        getCommand("plugman").setExecutor(cHandler);
        getCommand("plugman").setAliases(getAliases());
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

    public List<String> getAliases() {
        return aliases;
    }

    public List<String> getSkipped() {
        return skipPlugins;
    }

    public Utilities getUtils() {
        return util;
    }
    
    public PlugManCommands getExecutor() {
        return cHandler;
    }
}