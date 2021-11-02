package moe.quill.feather.old.selections.zones.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.tool.SelectToolManager
import moe.quill.feather.old.selections.zones.Zone
import moe.quill.feather.old.selections.zones.ZoneModule
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import java.util.ArrayList

class ZoneSaveCommand(private val zoneModule: ZoneModule, private val toolManager: SelectToolManager) :
    FeatherSubCommand {

    override fun getName(): String {
        return "save";
    }

    override fun getDescription(): String {
        return "Save the selected zone with the given name";
    }

    @Override
    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return

        //Check if they have something selected
        toolManager.getBounds(sender.uniqueId)?.let { bounds ->
            //If they did not supply a name let them know
            if (args.isEmpty()) {
                sender.sendMessage(Component.text("You must supply a name for this zone.").color(NamedTextColor.RED));
                return;
            }

            val zoneName = args[0];
            val existingZone = zoneModule.getZone(zoneName);
            if (existingZone != null) {
                sender.sendMessage(
                    Component.empty()
                        .append(Component.text("A zone already exists with the name ").color(NamedTextColor.RED))
                        .append(Component.text(String.format("'%s'", zoneName)).color(NamedTextColor.YELLOW))
                        .append(Component.text(" please delete it using ").color(NamedTextColor.RED))
                        .append(Component.text("/zone delete [name]").color(NamedTextColor.YELLOW))
                        .append(Component.text(".").color(NamedTextColor.RED))
                );
                return;
            }

            //Whether this has an infinite y-axis
            var infiniteY = false;
            if (args.size >= 2) {
                infiniteY = args[1].toBoolean()
            }

            zoneModule.addZone(Zone(zoneName, bounds, infiniteY = infiniteY));
            sender.sendMessage(
                Component.empty()
                    .append(Component.text("Registered a new zone with the name "))
                    .append(Component.text(zoneName))
                    .append(Component.text("!"))
                    .color(NamedTextColor.GREEN)
            );
        } ?: run {
            sender.sendMessage(
                Component.empty()
                    .append(Component.text("You do not have anything selected! Use").color(NamedTextColor.GRAY))
                    .append(Component.text(" /select tool").color(NamedTextColor.YELLOW))
                    .append(Component.text(" to get a tool for selection").color(NamedTextColor.GRAY))
            )
        }
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String> {
        if (args.size == 1) {
            return Collections.singletonList("[name]");
        } else if (args.size == 2) {
            return Collections.singletonList("infiniteY [true/false]");
        }
        return arrayListOf()
    }
}
