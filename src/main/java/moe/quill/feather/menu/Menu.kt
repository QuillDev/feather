package moe.quill.feather.menu

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class Menu(private val plugin: Plugin, private val size: Int, title: Component?) {

    //Map of items as they should appear in the inventory
    val items = HashMap<Int, MenuItem>()
    private val title: Component = title ?: Component.text("Menu");

    fun open(viewer: Player) {
        MenuInstance(plugin, this, viewer).open(viewer)
    }

    /**
     * Create an inventory with the items in this menu
     */
    fun createInventory(holder: InventoryHolder?): Inventory {
        val inventory = Bukkit.createInventory(holder, size, title)
        items.forEach { (idx, menuItem) -> inventory.setItem(idx, menuItem.item) }
        return inventory
    }

    /**
     * Set the item at the given index in the inventory to the given item stack
     */
    fun setItem(idx: Int, item: ItemStack) {
        setItem(idx, MenuItem(item));
    }

    fun setItem(idx: Int, item: MenuItem) {
        if (idx < 0 || idx > size - 1) {
            Bukkit.getLogger()
                .warning("Tried to register menu item with invalid index '%s'! (Max size is '%s'!)".format(idx, size))
            return;
        }
        items[idx] = item;
    }

}