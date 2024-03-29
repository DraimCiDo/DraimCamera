package net.draimcido.draimcamera.Config;

import net.draimcido.draimcamera.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * The type Message config.
 */
public class MessageConfig {

    private File file;
    private FileConfiguration configs;

    private Main plugin;

    /**
     * Instantiates a new Message config.
     *
     * @param plugin the plugin
     */
    public MessageConfig(Main plugin) {
        this.plugin = plugin;

        createConfigFile();
    }

    private void createConfigFile() {
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        configs = new YamlConfiguration();
        try {
            configs.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    public FileConfiguration getConfig() {
        return this.configs;
    }

    /**
     * Save config.
     */
    public void saveConfig() {
        try {
            this.configs.save(this.file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Could not save config.yml");
        }
    }

}
