package moe.quill.feather.structure.command

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class FeatherCommand(
    name: String,
    description: String,
    vararg aliases: String
) : Command(name, description, "", aliases.asList()),
    FeatherSubCommand {

    private var subCommands = HashMap<String, FeatherSubCommand>()

    fun registerSubCommand(vararg subCommands: FeatherSubCommand) {
        subCommands.forEach { this.subCommands[it.name()] = it }
    }

    //    Default Bukkit stuff we're overriding and making it pretty
    override fun execute(
        sender: CommandSender,
        commandLabel: String,
        args: Array<out String>
    ): Boolean {

        val argList = args.toList()
        //If we have no subCommands and the args are empty, execute the normal command
        if (subCommands.size == 0 || args.isEmpty()) {
            execute(sender, argList)
            return true
        }

        //Get the command with the map
        //Execute the sub commands executor if it exists
        //If args is more than 1, return a sliced copy of the args, otherwise just give them an empty list
        val subCommandName = args[0]
        val subCommand = subCommands[subCommandName]

        if (subCommand != null) {
            subCommand.execute(sender, if (argList.size > 1) argList.subList(1, argList.size) else ArrayList())
        } else {
            sender.sendMessage(Component.text("Could not find command with name '%s'!".format(subCommandName)))
        }
        return true
    }


    override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): MutableList<String> {
        val argList = args.toList()
        //If we have no subCommands and the args are empty, execute the normal command
        if (subCommands.size == 0) {
            return onTab(sender, argList).toMutableList()
        } else if (argList.size == 1) {
            return subCommands.keys.filter { it.contains(argList[0]) }.toCollection(arrayListOf())
        }
        //Get the command with the map
        //Execute the sub commands executor if it exists
        //If args is more than 1, return a sliced copy of the args, otherwise just give them an empty list
        val subCommandName = args[0]
        val subCommand = subCommands[subCommandName]

        if (subCommand != null) {
            return subCommand.onTab(sender, if (argList.size > 1) argList.subList(1, argList.size) else ArrayList()).toMutableList()
        }

        return arrayListOf()
    }


    override fun name(): String {
        return name
    }

    override fun description(): String {
        return description
    }
}