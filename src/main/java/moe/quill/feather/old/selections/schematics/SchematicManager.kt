package moe.quill.feather.old.selections.schematics;

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule
import moe.quill.feather.old.configuration.YamlConfigManager;
import moe.quill.feather.old.selections.schematics.commands.SchematicCommand;
import moe.quill.feather.old.selections.tool.SelectToolManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class SchematicManager(override val plugin: FeatherPlugin, private val toolManager: SelectToolManager) :
    YamlConfigManager(plugin, "schematics", "schematics.yml"),
    FeatherModule {

    private val schematicDataList: MutableMap<String, Schematic> = loadConfig();

    init {
        registerCommand(SchematicCommand(this, toolManager))
    }

    fun addSchematic(schematic: Schematic) {
        schematicDataList[schematic.name] = schematic;
        saveWorkingConfig();
    }

    fun removeSchematic(name: String): Boolean {
        val result = schematicDataList.remove(name);
        saveWorkingConfig();
        return result != null;
    }

    public fun getSchematic(name: String): Schematic? {
        return schematicDataList[name];
    }


    fun saveWorkingConfig() {
        val config = YamlConfiguration();
        config.set("schematics", schematicDataList.values.toList());
        saveConfig(config);
    }

    override fun loadConfig(): HashMap<String, Schematic> {
        val map = HashMap<String, Schematic>();

        val config = getYamlConfig();
        val schematics = config.getList("schematics")?.filterIsInstance<Schematic>() ?: return map;
        schematics.forEach { schematic -> map[schematic.name] = schematic };
        return map;
    }
}
