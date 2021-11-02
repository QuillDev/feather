package moe.quill.feather.old.kits;

import moe.quill.feather.lib.items.ItemBuilder
import org.bukkit.entity.Player

class TranslatableKit : Kit() {

    override fun giveKit(player: Player, clearInventory: Boolean, clearEffects: Boolean) {
        kitItems.forEach { it.item = ItemBuilder(it.item).translate(player.locale()).build() }
        armor.mapValues { ItemBuilder(it.value).translate(player.locale()).build() }

        super.giveKit(player, clearInventory, clearEffects);
    }
}
