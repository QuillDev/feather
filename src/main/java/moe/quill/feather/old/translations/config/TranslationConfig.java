package moe.quill.feather.old.translations.config;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TranslationConfig implements ConfigurationSerializable {

    private final Locale defaultLocale;

    public TranslationConfig(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    //Serialization
    public static TranslationConfig deserialize(Map<String, Object> map) {
        final var localeString = (String) map.getOrDefault("default-locale", "en");

        Locale locale;
        try {
            locale = Locale.forLanguageTag(localeString);
        } catch (NullPointerException ignored) {
            locale = Locale.ENGLISH;
        }

        return new TranslationConfig(locale);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("default-locale", defaultLocale.toLanguageTag());
        }};
    }


    //Getters
    public Locale getDefaultLocale() {
        return defaultLocale;
    }
}
