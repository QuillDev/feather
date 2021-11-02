package moe.quill.feather.lib.command

import org.bukkit.command.CommandSender

interface FeatherSubCommand {

    fun getName(): String
    fun getDescription(): String

    fun execute(sender: CommandSender, args: List<String>)
    fun tabComplete(sender: CommandSender, args: List<String>): List<String>?

}