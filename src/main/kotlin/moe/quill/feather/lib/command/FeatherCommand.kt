package moe.quill.feather.lib.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.command.defaults.BukkitCommand

abstract class FeatherCommand(
    name: String,
    description: String = "default",
    usage: String = "/$name",
    vararg alias: String
) : BukkitCommand(name, description, usage, alias.toList()), FeatherSubCommand {

    private val subCommands = HashMap<String, FeatherSubCommand>()

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {

        val prettyArgs = args.toList()

        //If we have sub commands
        if (subCommands.isNotEmpty()) {
            val subCommand = subCommands[prettyArgs[0]]
            if (subCommand == null) {
                execute(sender, prettyArgs)
                return true
            }

            subCommand.execute(sender, if (args.size == 1) ArrayList() else prettyArgs.subList(1, args.size))
        }

        execute(sender, prettyArgs)

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        val prettyArgs = args.toList()

        //If we have sub commands
        if (subCommands.isNotEmpty()) {
            val subCommand = subCommands[prettyArgs[0]] ?: return fixTabs(tabComplete(sender, prettyArgs))
            return fixTabs(subCommand.tabComplete(sender, getSubArgs(prettyArgs)))
        }

        return fixTabs(tabComplete(sender, prettyArgs))
    }

    private fun fixTabs(args: List<String>?): MutableList<String> {
        if (args == null) return mutableListOf()
        return args.toMutableList()
    }

    private fun getSubArgs(args: List<String>): List<String> {
        return if (args.size == 1) ArrayList() else args.subList(1, args.size)
    }

    protected fun filterTabs(tabs: List<String>, args: List<String>, caseSensitive: Boolean = false): List<String> {
        if (args.isEmpty()) return arrayListOf()
        val currentArgument = args[args.size - 1]
        return tabs.filter {
            if (caseSensitive) it.contains(currentArgument) else it.lowercase().contains(currentArgument.lowercase())
        }
    }
}