package moe.quill.feather.old.protection

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule
import moe.quill.feather.old.protection.commands.ProtectionCommand
import moe.quill.feather.old.protection.config.ProtectionConfig
import moe.quill.feather.old.protection.config.ProtectionConfigManager
import moe.quill.feather.old.protection.listeners.ManipulateBlockListener
import moe.quill.feather.old.selections.tool.SelectToolManager
import moe.quill.feather.old.selections.zones.Bounds
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerialization

class ProtectionModule(override val plugin: FeatherPlugin, toolManager: SelectToolManager) : FeatherModule {

    private val configManager = ProtectionConfigManager(plugin)
    var config: ProtectionConfig = configManager.loadConfig()

    init {
        registerCommand(ProtectionCommand(this, toolManager))
        registerListener(ManipulateBlockListener(this))
    }

    fun addArea(bounds: Bounds) {
        config.addArea(bounds)
        configManager.saveConfig(config.toYaml())
    }

    fun addArea(location: Location) {
        config.addArea(location)
        configManager.saveConfig(config.toYaml())
    }

    fun isExempt(location: Location): Boolean {
        return config.isExempt(location)
    }


    /**
     * Reload the configuration by loading the config again and setting the var here
     */
    fun reloadConfig() {
        this.config = configManager.loadConfig()
    }


    override fun registerSerializers() {
        ConfigurationSerialization.registerClass(ProtectionConfig::class.java, "ProtectionConfig")
    }
}
