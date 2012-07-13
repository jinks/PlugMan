package com.ryanclancy000.plugman;

import com.ryanclancy000.plugman.utilities.MetricsLite;
import com.ryanclancy000.plugman.utilities.Utilities;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {

    public List skipPlugins;
    private boolean useMetrics;
    //
    public final Utilities util = new Utilities(this);
    private static final Logger logger = Bukkit.getLogger();
    
    @Override
    public void onEnable() {
        getCommand("plugman").setExecutor(new PlugManCommands(this));
        loadConfig();
        startMetrics();
    }
    
    private void loadConfig() {
        try {
            useMetrics = this.getConfig().getBoolean("use-metrics");
            skipPlugins = this.getConfig().getList("skip-on-reload");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load config - ignoring skip-plugins feature!{0}", e);
            skipPlugins = null;
        }
    }

    private void startMetrics() {
        if (useMetrics) {
            try {
                MetricsLite metrics = new MetricsLite(this);
                metrics.start();
                logger.log(Level.INFO, "Metrics successfully started!");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to start Metrics!{0}", e);
            }
        } else {
            logger.log(Level.INFO, "Ignoring Metrics!");
        }
    }
}
