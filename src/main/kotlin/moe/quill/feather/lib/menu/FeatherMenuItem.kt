package moe.quill.feather.lib.menu

import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.ItemStack

class FeatherMenuItem(val item: ItemStack, val action: (HumanEntity) -> Unit = {})