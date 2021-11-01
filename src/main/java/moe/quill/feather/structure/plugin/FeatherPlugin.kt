package moe.quill.feather.structure.plugin

import moe.quill.feather.structure.module.Module
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

open class FeatherPlugin : JavaPlugin(), Module {

    //Module Extension Methods
    override fun server(): Server {
        return server;
    }

    override fun plugin(): JavaPlugin {
        return this;
    }
}