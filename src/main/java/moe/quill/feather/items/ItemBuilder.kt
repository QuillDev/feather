package moe.quill.feather.items

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemBuilder {

    private val item: ItemStack;

    constructor(type: Material) {
        this.item = ItemStack(type);
    }

    constructor(stack: ItemStack) {
        this.item = stack;
    }

    fun type(type: Material): ItemBuilder {
        if (!type.isItem || type.isAir) return this;

        this.item.type = type;
        return this;
    }

    fun displayName(name: Component): ItemBuilder {
        meta()?.also { it.displayName(name); meta(it) }
        return this
    }


    fun lore(lore: List<Component>): ItemBuilder {
        meta()?.also { it.lore(lore); meta(it) }
        return this
    }

    fun unbreakable(unbreakable: Boolean?): ItemBuilder {
        meta()?.also { it.isUnbreakable = unbreakable ?: true }
        return this;
    }

    fun meta(): ItemMeta? {
        return item.itemMeta;
    }

    fun meta(meta: ItemMeta) {
        this.item.itemMeta = meta;
    }

    /**
     * Build the builder into an actual item stack
     */
    fun build(): ItemStack {
        return this.item;
    }
}