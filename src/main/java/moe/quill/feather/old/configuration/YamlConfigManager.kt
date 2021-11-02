package moe.quill.feather.old.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract class YamlConfigManager(plugin: Plugin, vararg configChunks: String) {

    val configPath: Path = Paths.get(plugin.dataFolder.toPath().toString(), *configChunks);
    val configFile: File = configPath.toFile();

    init {
        ensureConfigExists()
    }

    /**
     * Attempt to ensure that the config file for this config manager
     * exists and write the default config to it if it does not exist
     */
    fun ensureConfigExists() {
        //if the config file exists, just return out
        if (configFile.exists()) return;
        saveConfig(getDefaultConfig());
    }

    /**
     * Save the config of the given configuration to the file
     *
     * @param configuration to save
     */
    fun saveConfig(configuration: YamlConfiguration) {
        try {
            Files.createDirectories(configPath.parent);
            Files.writeString(configPath, configuration.saveToString());
        } catch (e: IOException) {
            e.printStackTrace();
        }
    }

    /**
     * Method for loading configuration to the specified file type
     *
     * @return the loaded config object
     */
    abstract fun loadConfig(): Any;

    /**
     * Get the yaml configuration for the
     * config file at the provided path
     *
     * @return the YAML configuration for the given config file
     */
    fun getYamlConfig(): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Get the default yaml configuration of this file
     * for the purpose of writing it to newly created
     * configuration files
     *
     * @return the default config for the given yaml config
     */
    open fun getDefaultConfig(): YamlConfiguration {
        return YamlConfiguration();
    }
}
