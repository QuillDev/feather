package moe.quill.feather.old.selections.zones;

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule
import moe.quill.feather.old.configuration.YamlConfigManager
import moe.quill.feather.old.selections.tool.SelectToolManager
import moe.quill.feather.old.selections.zones.commands.ZoneCommand
import java.util.*
import java.util.ArrayList
import java.util.HashMap
import java.util.stream.Collectors

class ZoneModule(override val plugin: FeatherPlugin, toolManager: SelectToolManager) :
    YamlConfigManager(plugin, "zones", "zones.yml"), FeatherModule {

    val zoneMap: HashMap<String, Zone> = loadConfig();

    init {
        registerCommand(ZoneCommand(this, toolManager))
    }


    override fun loadConfig(): HashMap<String, Zone> {
        val zoneMap = HashMap<String, Zone>()
        val config = getYamlConfig();

        //Iterate through the zone map
        val zones = config.getList("zone-data", ArrayList<Zone>())?.filterIsInstance<Zone>()
        zones?.forEach { zone ->
            plugin.logger.info("Loaded zone with name: " + zone.name);
            zoneMap[zone.name] = zone;
        }
        return zoneMap;
    }

    /**
     * Add the given name
     *
     * @param zone to add to the zone map
     */
    fun addZone(zone: Zone) {
        zoneMap[zone.name] = zone;
        saveWorkingConfig();
    }

    /**
     * Remove the zone from the zone map
     *
     * @param name of the zone to remove
     */
    fun removeZone(name: String) {
        zoneMap.remove(name);
        saveWorkingConfig();
    }

    /**
     * Get the zone map for the given name
     *
     * @param name of the zone to get
     * @return the zone for this name
     */
    fun getZone(name: String): Zone? {
        return zoneMap[name];
    }

    fun zones(zoneNames: List<String>): List<Zone> {
        return zoneNames.mapNotNull(this::getZone).toList()
    }

    /**
     * Save the working config of active zones
     */
    fun saveWorkingConfig() {
        val config = getYamlConfig();
        config.set("zone-data", zoneMap.values.toList());
        saveConfig(config);
    }
}
