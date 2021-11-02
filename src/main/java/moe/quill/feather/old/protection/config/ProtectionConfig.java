package moe.quill.feather.old.protection.config;

import moe.quill.feather.old.selections.zones.Bounds;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtectionConfig implements ConfigurationSerializable {

    private final List<Location> locations;
    private final List<Bounds> bounds;

    public ProtectionConfig(List<Location> locations, List<Bounds> regions) {
        this.locations = locations;
        this.bounds = regions;
    }

    public ProtectionConfig() {
        this.locations = new ArrayList<>();
        this.bounds = new ArrayList<>();
    }

    public void addArea(Bounds bounds) {
        this.bounds.add(bounds);
    }

    public void addArea(Location location) {
        this.locations.add(location);
    }

    /**
     * Check if this area is exempt from protections
     *
     * @param query of the location to check
     * @return whether the given area is exempt
     */
    public boolean isExempt(Location query) {
        for (final var bounds : bounds) {
            if (!bounds.isInBounds(query)) continue;
            return true;
        }

        for (final var location : locations) {
            if (!location.equals(query)) continue;
            return true;
        }

        return false;
    }

    public static ProtectionConfig deserialize(Map<String, Object> map) {
        final var locations = (List<Location>) map.getOrDefault("locations", new ArrayList<>());
        final var regions = (List<Bounds>) map.getOrDefault("regions", new ArrayList<>());
        return new ProtectionConfig(locations, regions);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("locations", locations);
            put("regions", bounds);
        }};
    }

    public YamlConfiguration toYaml() {
        final var configuration = new YamlConfiguration();
        configuration.set("exempt-regions", this);

        return configuration;
    }
}
