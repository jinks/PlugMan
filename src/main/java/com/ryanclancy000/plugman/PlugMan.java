package com.ryanclancy000.plugman;

import com.ryanclancy000.plugman.utilities.MetricsLite;
import com.ryanclancy000.plugman.utilities.Utilities;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugMan extends JavaPlugin
{
  public List skipPlugins;
  private transient boolean useMetrics;
  public final Utilities util = new Utilities(this);
  public static final Logger logger = Bukkit.getLogger();

  public void onEnable()
  {
    getCommand("plugman").setExecutor(new PlugManCommands(this));
    loadConfig();
    startMetrics();
  }

  private void loadConfig() {
    try {
      this.useMetrics = getConfig().getBoolean("use-metrics");
      this.skipPlugins = getConfig().getList("skip-on-reload");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to load config - ignoring skip-plugins feature!{0}", e);
      this.useMetrics = true;
      this.skipPlugins = null;
    }
  }

  private void startMetrics() {
    if (this.useMetrics)
      try {
        MetricsLite metrics = new MetricsLite(this);
        metrics.start();
        logger.log(Level.INFO, "Metrics successfully started!");
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Failed to start Metrics!{0}", e);
      }
    else
      logger.log(Level.INFO, "Ignoring Metrics!");
  }
}