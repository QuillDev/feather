package moe.quill.feather.lib.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
            val subCommand = if (prettyArgs.isNotEmpty()) subCommands[prettyArgs[0]] else null
            if (subCommand == null) {
                sender.sendMessage(Component.text("There is no sub command with that name!").color(NamedTextColor.RED))
                return true
            }

            subCommand.execute(sender, if (args.size == 1) ArrayList() else prettyArgs.subList(1, args.size))
            return true
        }

        execute(sender, prettyArgs)
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        val prettyArgs = args.toList()

        //If we have sub commands
        if (subCommands.isNotEmpty()) {
            val subCommand = subCommands[prettyArgs[0]]
                ?: return fixTabs(filterTabs(subCommands.keys.toList(), prettyArgs))
            return fixTabs(subCommand.tabComplete(sender, getSubArgs(prettyArgs)))
        }

        return fixTabs(tabComplete(sender, prettyArgs))
    }

    fun registerSubCommand(vararg subCommands: FeatherSubCommand) {
        subCommands.forEach { this.subCommands[it.getName()] = it }
    }

    private fun fixTabs(args: List<String>?): MutableList<String> {
        if (args == null) return mutableListOf()
        return args.toMutableList()
    }

    private fun getSubArgs(args: List<String>): List<String> {
        return if (args.size == 1) ArrayList() else args.subList(1, args.size)
    }
}