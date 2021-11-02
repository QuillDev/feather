package moe.quill.featherplug.listeners

import moe.quill.feather.lib.items.ItemBuilder
import moe.quill.feather.lib.lambda.FeatherRunnable
import moe.quill.feather.lib.menu.FeatherMenu
import moe.quill.feather.lib.menu.FeatherMenuItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class MenuListener(private val plugin: Plugin) : Listener {

    private val menu = FeatherMenu(plugin, 3, Component.text("Test Menu!"))

    init {
        menu.setItem(
            0, FeatherMenuItem(
                ItemBuilder(Material.FEATHER).displayName(Component.text("Dev Feather")).build()
            ) { player -> player.sendMessage(Component.text("You clicked it")) }
        )
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        FeatherRunnable { menu.openMenu(event.player) }.runTaskLater(plugin, 1)
    }
}