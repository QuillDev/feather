package moe.quill.featherplug.commands

import moe.quill.feather.lib.command.FeatherSubCommand
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.CommandSender

class DevSub : FeatherSubCommand {
    override fun getName(): String {
        return "sub"
    }

    override fun getDescription(): String {
        return "nothing"
    }

    override fun execute(sender: CommandSender, args: List<String>) {
        sender.sendMessage(Component.text("Sub command"))
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return filterTabs(Material.values().map { it.name }, args)
    }
}