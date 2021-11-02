package moe.quill.feather.old.selections.location.commands;

import moe.quill.feather.lib.command.FeatherCommand
import moe.quill.feather.old.selections.location.LocationModule
import moe.quill.feather.old.selections.tool.SelectToolManager
import org.bukkit.command.CommandSender

class LocationCommand(locationModule: LocationModule, toolManager: SelectToolManager) :
    FeatherCommand("location", "show location help") {

    init {
        registerSubCommand(
            LocationSaveCommand(locationModule, toolManager),
            LocationDeleteCommand(locationModule, toolManager),
            LocationTP(locationModule)
        )
    }

    override fun execute(sender: CommandSender, args: List<String>) {

    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null;
    }

}
