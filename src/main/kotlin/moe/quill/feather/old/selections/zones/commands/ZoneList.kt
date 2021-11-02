package moe.quill.feather.old.selections.zones.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.zones.ZoneModule
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

class ZoneList(private val zoneModule: ZoneModule) : FeatherSubCommand {

    override fun getName(): String {
        return "list";
    }

    @Override
    override fun getDescription(): String {
        return "Lists all configured zones.";
    }

    @Override
    override fun execute(sender: CommandSender, args: List<String>) {

        //Get the zones and a component for generating output
        var output = Component.empty();
        val zones = ArrayList(zoneModule.zoneMap.values.toList())

        //If there are no zones, tell the player
        if (zones.size == 0) {
            sender.sendMessage((Component.text("There are no zones saved.").color(NamedTextColor.RED)));
            return;
        }

        //Generate the zone list
        for (idx in 0..zones.size) {
            val zone = zones[idx];
            output = output.append(Component.text(zone.name + ": ")).append(Component.text(zone.toString()));

            //Append newlines before the last entry
            if (idx < zones.size - 1) {
                output = output.append(Component.newline());
            }
        }

        sender.sendMessage(output);
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }
}
