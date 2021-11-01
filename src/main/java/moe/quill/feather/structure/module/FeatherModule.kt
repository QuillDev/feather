package moe.quill.feather.structure.module

import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

class FeatherModule(private val plugin: JavaPlugin) : Module {


    //Default Methods from module
    override fun server(): Server {
        return plugin().server;
    }

    override fun plugin(): JavaPlugin {
        return plugin
    }
}