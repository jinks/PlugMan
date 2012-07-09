package me.ryanclancy000.plugman;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin {

    public List skipPlugins;
    public PluginDescriptionFile PDF;
    public final Utilities util = new Utilities(this);
    public final PlugManCommands cHandler = new PlugManCommands(this);

    @Override
    public void onEnable() {
        PDF = this.getDescription();
        getCommand("plugman").setExecutor(cHandler);
        startMetrics();
        loadConfig();
    }

    public void startMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (Exception e) {
            this.getLogger().severe("Failed to load Metrics tracking!");
        }
    }

    public void loadConfig() {
        try {
            this.getConfig().options().copyDefaults(true);
            skipPlugins = this.getConfig().getList("skip-on-reload");
            this.saveConfig();
        } catch (Exception e) {
            this.getLogger().severe("Failed to load config, disabling PlugMan");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

}
