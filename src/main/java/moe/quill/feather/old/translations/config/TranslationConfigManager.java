package moe.quill.feather.old.translations.config;

import moe.quill.feather.lib.structure.FeatherPlugin;
import moe.quill.feather.old.configuration.YamlConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Locale;

public class TranslationConfigManager extends YamlConfigManager {

    private final TranslationConfig config;

    public TranslationConfigManager(FeatherPlugin plugin) {
        super(plugin, "translations.yml");
        this.config = loadConfig();
    }

    public TranslationConfig getConfig() {
        return config;
    }

    @Override
    public TranslationConfig loadConfig() {
        return (TranslationConfig) getYamlConfig().get("config");
    }

    @Override
    public YamlConfiguration getDefaultConfig() {
        final var yamlConfig = new YamlConfiguration();
        yamlConfig.set("config", new TranslationConfig(Locale.ENGLISH));
        return yamlConfig;
    }
}
