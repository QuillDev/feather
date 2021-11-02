package moe.quill.feather.old.protection.commands;

import moe.quill.feather.lib.command.FeatherCommand
import moe.quill.feather.old.protection.ProtectionModule
import moe.quill.feather.old.protection.commands.subcommands.AddBreakableLocation
import moe.quill.feather.old.protection.commands.subcommands.AddBreakableRegion
import moe.quill.feather.old.selections.tool.SelectToolManager
import org.bukkit.command.CommandSender

class ProtectionCommand(protectionModule: ProtectionModule, private val toolManager: SelectToolManager) :
    FeatherCommand("prot", "Shows help for the protection commands") {

    init {
        registerSubCommand(
            AddBreakableRegion(protectionModule, toolManager),
            AddBreakableLocation(protectionModule, toolManager)
        )
    }

    override fun execute(sender: CommandSender, args: List<String>) {
    }

    override fun tabComplete(
        sender: CommandSender,
        args: List<String>
    ): List<String>? {
        return null
    }
}
