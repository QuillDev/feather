package moe.quill.featherplug

import moe.quill.feather.lib.command.FeatherCommand
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType

class DevCommand : FeatherCommand("dev") {
    override fun execute(sender: CommandSender, args: List<String>) {
        sender.sendMessage(Component.text("Hello!"))
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return filterTabs(EntityType.values().map { it.name }, args)
    }
}