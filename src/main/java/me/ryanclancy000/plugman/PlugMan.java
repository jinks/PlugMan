package me.ryanclancy000.plugman;

import java.util.List;
import java.util.logging.Level;
import me.ryanclancy000.plugman.utilities.Metrics;
import me.ryanclancy000.plugman.utilities.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {

    public List skipPlugins;
    public final Utilities util = new Utilities(this);

    @Override
    public void onEnable() {
        getCommand("plugman").setExecutor(new PlugManCommands(this));
        startMetrics();
        loadConfig();
    }

    public void startMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Failed to load Metrics tracking!{0}", e);
        }
    }

    public void loadConfig() {
        try {
            skipPlugins = this.getConfig().getList("skip-on-reload");
        } catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Failed to load config - ignoring skip-plugins feature!{0}", e);
            skipPlugins = null;
        }
    }
}
