package moe.quill.feather.menu

import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.ItemStack

class MenuItem(val item: ItemStack, val action: (clicker: HumanEntity) -> Unit = {})