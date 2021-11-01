package moe.quill.feather.structure.command

import org.bukkit.command.CommandSender

interface FeatherSubCommand {

    fun name(): String
    fun description(): String

    fun onTab(sender: CommandSender, args: List<String>): List<String>
    fun execute(sender: CommandSender, args: List<String>)
}