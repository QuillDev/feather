package moe.quill.feather.structure.module

import moe.quill.feather.structure.command.FeatherCommand
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import org.bukkit.Server
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

interface Module : ForwardingAudience {

    fun server(): Server
    fun plugin(): JavaPlugin

    //Registering Commands
    fun registerCommand(vararg commands: FeatherCommand) {
        commands.forEach {
            server().commandMap.register(plugin().name, it)
        }
    }

    //Registering Listeners
    fun registerListener(vararg listeners: Listener) {
        listeners.forEach { server().pluginManager.registerEvents(it, plugin()) }
    }

    fun unregisterListener(vararg listeners: Listener) {
        listeners.forEach { HandlerList.unregisterAll(it) }
    }

    //Audiences
    override fun audiences(): MutableIterable<Audience> {
        return server().audiences()
    }
}