package moe.quill.feather.lib.menu

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.plugin.Plugin

class FeatherMenu(
    private val plugin: Plugin,
    private val rows: Int = 3,
    private val title: Component = Component.text("Menu")
) {

    private val contents = HashMap<Int, FeatherMenuItem>()

    fun setItem(slot: Int, item: FeatherMenuItem) {
        if (slot > rows) {
            Bukkit.getLogger()
                .warning("Tried to register an invalid slot in menu ${(title as TextComponent).content()}")
            return
        }
        contents[slot] = item
    }

    fun openMenu(viewer: HumanEntity) {
        val instance = createInstance()
        instance.addViewer(viewer)
    }

    //Create an instance of this menu that can be shown to players
    fun createInstance(): FeatherMenuInstance {
        return FeatherMenuInstance(plugin, contents, rows, title)
    }
}