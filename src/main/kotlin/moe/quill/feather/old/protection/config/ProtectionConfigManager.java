package moe.quill.feather.old.protection.config;

import moe.quill.feather.old.configuration.YamlConfigManager;
import org.bukkit.plugin.Plugin;

public class ProtectionConfigManager extends YamlConfigManager {
    /**
     * Constructor for creating yaml configs from
     * A relative path string which is where the YAML
     * Config will be stored
     *
     * @param plugin to register this config under
     */
    public ProtectionConfigManager(Plugin plugin) {
        super(plugin, "protection.yml");
    }

    @Override
    public ProtectionConfig loadConfig() {
        final var config = getYamlConfig();
        final var protectionConfig = (ProtectionConfig) config.get("exempt-regions", new ProtectionConfig());
        if (protectionConfig == null) return new ProtectionConfig();
        return protectionConfig;
    }
}
