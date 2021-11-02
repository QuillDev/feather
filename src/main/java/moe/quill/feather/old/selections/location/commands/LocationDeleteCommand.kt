package moe.quill.feather.old.selections.location.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.location.LocationModule
import moe.quill.feather.old.selections.tool.SelectToolManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LocationDeleteCommand(private val locationModule: LocationModule, private val toolManager: SelectToolManager) :
    FeatherSubCommand {

    override fun getName(): String {
        return "delete";
    }

    override fun getDescription(): String {
        return "save the selected location";
    }

    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return

        if (args.isEmpty()) {
            sender.sendMessage(Component.text("You must supply a location").color(NamedTextColor.RED))
            return
        }

        val name = args[0]

        //If we found the location
        locationModule.findLocation(name)?.let {
            locationModule.removeLocation(name)
            sender.sendMessage(
                Component.text()
                    .append(Component.text("You successfully deleted the location with the name '"))
                    .append(Component.text(name))
                    .append(Component.text("'!"))
                    .color(NamedTextColor.GREEN)
            )
            //If we didn't find the location
        } ?: run {
            sender.sendMessage(
                Component.text("Could not find a location with the name $name").color(NamedTextColor.RED)
            )
        }

    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null;
    }
}
