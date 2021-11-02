package moe.quill.feather.old.selections.location.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.location.LocationModule
import moe.quill.feather.old.selections.tool.SelectToolManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LocationSaveCommand(private val locationModule: LocationModule, private val toolManager: SelectToolManager) :
    FeatherSubCommand {

    override fun getName(): String {
        return "save";
    }

    @Override
    override fun getDescription(): String {
        return "save the selected location";
    }

    override fun execute(sender: CommandSender, args: List<String>) {

        if (sender !is Player) return

        //Get the left click
        toolManager.getLeftClick(sender.uniqueId)?.let {
            //If it exists process it
            if (args.isEmpty()) {
                sender.sendMessage(
                    Component.text("You must specify a name for the selection").color(NamedTextColor.RED)
                );
                return;
            }

            //Check if we already have a location with this name
            val name = args[0]
            locationModule.findLocation(name)?.let {
                sender.sendMessage(
                    Component.text("A location already exists with the name '")
                        .append(Component.text(name))
                        .append(Component.text("'!"))
                        .color(NamedTextColor.RED)
                )
            } ?: run {
                locationModule.addLocation(name, it);
                sender.sendMessage(
                    Component.text()
                        .append(Component.text("You successfully save a new location with the name '"))
                        .append(Component.text(name))
                        .append(Component.text("'!"))
                        .color(NamedTextColor.GREEN)
                );
            }
        } ?: run {
            sender.sendMessage(
                Component.text("You must select a zone using the tool in /select tool").color(NamedTextColor.RED)
            )
        }
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }
}
