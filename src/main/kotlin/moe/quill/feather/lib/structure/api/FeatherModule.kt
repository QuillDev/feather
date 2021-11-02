package moe.quill.feather.lib.structure.api

import org.bukkit.plugin.java.JavaPlugin

interface FeatherModule : FeatherBase {
    val plugin: JavaPlugin

    override fun plugin(): JavaPlugin {
        return plugin
    }
}