package com.ryanclancy000.plugman.utilities;

import com.ryanclancy000.plugman.PlugMan;
import java.io.File;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class Configuration extends ConfigurationHolderConfiguration {
    
    public Configuration(PlugMan plugin) {
        super(new YamlConfiguration(new File(plugin.getDataFolder(), "config.yml")));
    }
}
