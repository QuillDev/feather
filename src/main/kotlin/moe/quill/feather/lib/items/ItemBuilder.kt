package moe.quill.feather.lib.items

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemBuilder {

    private val item: ItemStack

    constructor(item: ItemStack) {
        this.item = item
    }

    constructor(type: Material) {
        this.item = ItemStack(type)
    }

    //Item Type
    fun type(type: Material): ItemBuilder {
        item.type = type
        return this
    }

    //Display name
    fun displayName(displayName: Component): ItemBuilder {
        meta()?.also { it.displayName(displayName); meta(it) }
        return this
    }

    //Lore
    fun lore(lore: List<Component>): ItemBuilder {
        meta()?.also { it.lore(lore); meta(it) }
        return this
    }

    //Unbreakable
    fun unbreakable(unbreakable: Boolean): ItemBuilder {
        meta()?.also { it.isUnbreakable = unbreakable; meta(it) }
        return this
    }

    //Meta
    fun meta(): ItemMeta? {
        return item.itemMeta
    }

    fun meta(meta: ItemMeta): ItemBuilder {
        item.itemMeta = meta
        return this
    }

    fun build(): ItemStack {
        return item
    }
}