package moe.quill.feather.lib.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.translation.GlobalTranslator
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors

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

    //Quantity
    fun amount(amount: Int): ItemBuilder {
        item.amount = amount
        return this
    }

    //Modify attributes
    fun addAttribute(attribute: Attribute, modifier: AttributeModifier): ItemBuilder {
        meta()?.also { it.addAttributeModifier(attribute, modifier); meta(it) }
        return this
    }

    fun addAttribute(
        attribute: Attribute,
        name: String,
        modifier: Double,
        operation: AttributeModifier.Operation
    ): ItemBuilder {
        return addAttribute(attribute, AttributeModifier(name, modifier, operation))
    }

    fun <T, Z : Any> applyKey(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z): ItemBuilder {
        meta()?.also { it.persistentDataContainer.set(key, type, value) }
        return this
    }

    fun applyEnchant(enchantment: Enchantment, level: Int): ItemBuilder {
        try {
            item.addEnchantment(enchantment, level)
        } catch (exception: IllegalArgumentException) {
        }
        return this
    }

    fun applyMarkerKey(key: NamespacedKey): ItemBuilder {
        return applyKey(key, PersistentDataType.STRING, "")
    }

    //Display name
    fun displayName(displayName: Component): ItemBuilder {
        meta()?.also { it.displayName(displayName); meta(it) }
        return this
    }

    fun displayName(): Component? {
        return meta()?.displayName()
    }

    //Lore
    fun lore(lore: List<Component>): ItemBuilder {
        meta()?.also { it.lore(lore); meta(it) }
        return this
    }

    fun lore(): List<Component> {
        return meta()?.lore() ?: arrayListOf()
    }

    //Translation
    fun translate(locale: Locale): ItemBuilder {
        displayName()?.also { displayName(GlobalTranslator.render(it, locale)) }
        lore(lore().map { GlobalTranslator.render(it, locale) })
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