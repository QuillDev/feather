package moe.quill.feather.old.selections.zones.commands;

import moe.quill.feather.lib.command.FeatherCommand
import moe.quill.feather.old.selections.tool.SelectToolManager
import moe.quill.feather.old.selections.zones.ZoneModule
import moe.quill.feather.old.selections.zones.utils.LocationUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ZoneCommand(private val zoneModule: ZoneModule, private val toolManager: SelectToolManager) :
    FeatherCommand("zone", "gives the zone you're currently in") {

    init {
        registerSubCommand(
            ZoneSaveCommand(zoneModule, toolManager),
            ZoneList(zoneModule),
            ZoneDeleteCommand(zoneModule)
        )

    }

    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return

        //Get the zone list
        val location = sender.location

        val zones = zoneModule.zoneMap.values.filter { zone -> zone.isInBounds(location) }

        //If there are no zones tell the player
        if (zones.isEmpty()) {
            sender.sendMessage(
                Component.text("You are not currently standing in any zones.").color(NamedTextColor.RED)
            );
            return;
        }

        val output = LocationUtils.getZoneListComponent(zones);
        sender.sendMessage(output);
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }

}
