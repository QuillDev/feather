package moe.quill.feather.old.selections

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule

import moe.quill.feather.old.protection.ProtectionModule
import moe.quill.feather.old.selections.location.LocationModule
import moe.quill.feather.old.selections.schematics.Schematic
import moe.quill.feather.old.selections.schematics.SchematicData
import moe.quill.feather.old.selections.schematics.SchematicManager
import moe.quill.feather.old.selections.tool.SelectToolManager
import moe.quill.feather.old.selections.zones.Bounds
import moe.quill.feather.old.selections.zones.Zone
import moe.quill.feather.old.selections.zones.ZoneModule
import org.bukkit.configuration.serialization.ConfigurationSerialization

class SelectionModule(override val plugin: FeatherPlugin) : FeatherModule {

    val toolManager = SelectToolManager(plugin)
    val zoneModule = ZoneModule(plugin, toolManager)
    val schematicManager = SchematicManager(plugin, toolManager)
    val protectionModule = ProtectionModule(plugin, toolManager)
    val locationModule = LocationModule(plugin, toolManager)

    @Override
    override fun registerSerializers() {
        ConfigurationSerialization.registerClass(Bounds::class.java, "Bounds")
        ConfigurationSerialization.registerClass(Zone::class.java, "Zone")
        ConfigurationSerialization.registerClass(SchematicData::class.java, "SchematicData")
        ConfigurationSerialization.registerClass(Schematic::class.java, "Schematic")
    }
}
