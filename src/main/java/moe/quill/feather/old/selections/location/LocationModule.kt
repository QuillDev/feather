package moe.quill.feather.old.selections.location;

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule
import moe.quill.feather.old.configuration.YamlConfigManager
import moe.quill.feather.old.selections.location.commands.LocationCommand
import moe.quill.feather.old.selections.tool.SelectToolManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.util.*
import java.util.HashMap
import java.util.stream.Collectors

class LocationModule(override val plugin: FeatherPlugin, private val toolManager: SelectToolManager) :
    YamlConfigManager(plugin), FeatherModule {

    val locations: HashMap<String, Location> = loadConfig();

    init {
        registerCommand(LocationCommand(this, toolManager))
    }

    /**
     * Try to find the given location from hte map
     *
     * @param query to search for
     * @return the location
     */
    fun findLocation(query: String): Location? {
        return locations[query]
    }

    /**
     * Add the given location to the location manager
     *
     * @param name     to register this location under
     * @param location to register
     */
    fun addLocation(name: String, location: Location) {
        locations[name] = location;
        saveWorkingConfig();
    }

    /**
     * Remove the location with the given name
     *
     * @param name of the location to remove
     * @return whether there was a location with this name
     */
    fun removeLocation(name: String): Boolean {
        val success = locations.remove(name) != null;

        //Only worth saving if we actually removed the location
        if (success) {
            saveWorkingConfig();
        }

        return success;
    }

    /**
     * Save the working configuration
     */
    fun saveWorkingConfig() {
        val config = YamlConfiguration();
        locations.forEach(config::set);
        saveConfig(config);
    }

    override fun loadConfig(): HashMap<String, Location> {

        val config = HashMap<String, Location>()

        getYamlConfig().getKeys(false).forEach {
            val location = config[it]
            if (location !is Location) return@forEach
            config[it] = location
        }

        return config
    }
}
