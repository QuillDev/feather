package moe.quill.feather.lib.menu

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

class FeatherMenuInstance(
    plugin: Plugin,
    private val contents: HashMap<Int, FeatherMenuItem> = HashMap(),
    rows: Int = 3,
    title: Component = Component.text("Menu"),
    private val cancelClicks: Boolean = true
) : Listener {

    private val inventory: Inventory

    init {
        //Populate the inventory
        this.inventory = Bukkit.createInventory(null, rows * 9, title)
        contents.forEach { (slot, menuItem) -> inventory.setItem(slot, menuItem.item) }

        //Register this plugin with the plugin manager
        Bukkit.getServer().pluginManager.registerEvents(this, plugin)
    }

    fun addViewer(entity: HumanEntity) {
        entity.openInventory(inventory)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.inventory != inventory) return
        event.isCancelled = cancelClicks
        contents[event.slot]?.also { it.action(event.whoClicked) }
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.viewers.size == 0) {
            HandlerList.unregisterAll(this)
        }
    }
}