package moe.quill.feather.lib.structure.api

import moe.quill.feather.lib.command.FeatherCommand
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

interface FeatherBase {

    fun registerListener(vararg listener: Listener) {
        listener.forEach { Bukkit.getServer().pluginManager.registerEvents(it, plugin()) }
    }

    fun unregisterListener(vararg listener: Listener) {
        listener.forEach { HandlerList.unregisterAll(it) }
    }

    fun registerCommand(vararg commands: FeatherCommand) {
        commands.forEach {
            plugin().server.commandMap.register(plugin().name, it)
        }
    }

    fun plugin(): JavaPlugin
}