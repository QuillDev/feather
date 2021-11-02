package moe.quill.feather.old.selections.commands;

import moe.quill.feather.lib.command.FeatherCommand
import moe.quill.feather.old.selections.tool.SelectToolManager
import org.bukkit.command.CommandSender

class SelectCommand(toolManager: SelectToolManager) : FeatherCommand("select") {

    init {
        registerSubCommand(SelectToolCommand(toolManager))
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
