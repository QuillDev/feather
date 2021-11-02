package moe.quill.feather.lib.command

import org.bukkit.command.CommandSender

interface FeatherSubCommand {

    fun getName(): String
    fun getDescription(): String

    fun execute(sender: CommandSender, args: List<String>)
    fun tabComplete(sender: CommandSender, args: List<String>): List<String>?

    fun filterTabs(tabs: List<String>, args: List<String>, caseSensitive: Boolean = false): List<String> {
        if (args.isEmpty()) return arrayListOf()
        val currentArgument = args[args.size - 1]
        return tabs.filter {
            if (caseSensitive) it.contains(currentArgument) else it.lowercase().contains(currentArgument.lowercase())
        }
    }

}