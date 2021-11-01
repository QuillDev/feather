package moe.quill.feather.menu

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.Plugin

/**
 * This class is an 'instance' or copy of the master menu
 * which can be used on HumanEntities to allow them to se
 * the inventory, once all users close the inventory any registers in it are
 * unregistered.
 */
class MenuInstance(
    private val plugin: Plugin,
    private val menu: Menu,
    holder: InventoryHolder?,
    private var blockClicks: Boolean = true
) : Listener {

    private val inventory: Inventory = menu.createInventory(holder);

    /**
     * Open the inventory for the given viewer and register the events
     * for this menu instance
     */
    fun open(viewer: HumanEntity) {
        //If there are zero people currently viewing the inventory then register
        //Its actions into the event loop
        if (inventory.viewers.size == 0) {
            //Register the events from this inventory instance
            Bukkit.getServer().pluginManager.registerEvents(this, plugin)
        }

        //Open the inventory for the viewer
        viewer.openInventory(inventory)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.inventory != inventory) return
        menu.items[event.slot]?.also { it.action(event.whoClicked) }

        //Cancel the client
        event.isCancelled = blockClicks
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.inventory != inventory) return;
        if (inventory.viewers.size > 0) return

        //If no one is viewing this inventory any more
        //De register it from the event loop
        HandlerList.unregisterAll(this)
    }

}