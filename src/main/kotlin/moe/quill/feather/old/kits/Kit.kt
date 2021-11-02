package moe.quill.feather.old.kits

import moe.quill.feather.old.InventoryUtils
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import java.util.*

open class Kit(
    val kitItems: HashSet<KitItem> = HashSet(),
    val armor: EnumMap<EquipmentSlot, ItemStack> = EnumMap(EquipmentSlot::class.java),
    private val effects: HashSet<PotionEffect> = HashSet(),
    private val attributes: EnumMap<Attribute, Double> = EnumMap(Attribute::class.java)
) {


    /**
     * Add an item to the kit
     *
     * @param item to add to the kit
     */
    fun addItem(item: ItemStack) {
        kitItems.add(KitItem(item))
    }

    fun addItem(item: KitItem) {
        kitItems.add(item)
    }


    /**
     * Add an attribute for the given amount
     *
     * @param attribute the attribute to modify
     * @param amount    to set that attribute to
     */
    fun addAttribute(attribute: Attribute, amount: Double) {
        this.attributes[attribute] = amount
    }

    /**
     * Add the given potion effect
     *
     * @param effect to add
     */
    fun addEffect(effect: PotionEffect) {
        this.effects.add(effect)
    }

    /**
     * Set the given armor slot for this class to the given armor value
     *
     * @param slot to set the slot for
     * @param item to set the slot for
     */
    fun setArmor(slot: EquipmentSlot, item: ItemStack) {
        armor[slot] = item
    }

    /**
     * Give the kit to the given player and clear their inventory
     * if requested
     *
     * @param player         to give the kit to
     * @param clearInventory whether to clear their inventory
     */
    open fun giveKit(player: Player, clearInventory: Boolean = true, clearEffects: Boolean = true) {
        val inventory = player.inventory

        //Clear inventory if specified
        if (clearInventory) {
            inventory.clear()
        }
        //Remove effects if specified
        if (clearEffects) {
            for (effect in player.activePotionEffects) {
                player.removePotionEffect(effect.type)
            }
        }

        armor.forEach { (slot, item) -> InventoryUtils.setEquipment(player, item, slot) }

        //Apply attributes
        attributes.forEach { (attr, amount) ->
            var existingAttribute = player.getAttribute(attr)
            if (existingAttribute == null) {
                player.registerAttribute(attr)
                existingAttribute = player.getAttribute(attr)
            }
            //Apply the value if possible
            if (existingAttribute == null) return
            existingAttribute.baseValue = amount

            //Add items to the players inventory
            kitItems.sorted().forEach { kitItem -> inventory.addItem(kitItem.item) }
            player.addPotionEffects(effects)
        }


    }
}
