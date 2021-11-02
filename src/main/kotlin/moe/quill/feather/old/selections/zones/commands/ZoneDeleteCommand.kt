package moe.quill.feather.old.selections.zones.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.zones.ZoneModule
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

class ZoneDeleteCommand(private val zoneModule: ZoneModule) : FeatherSubCommand {

    override fun getName(): String {
        return "delete";
    }

    override fun getDescription(): String {
        return "Delete the zone with the given name.";
    }

    @Override
    override fun execute(sender: CommandSender, args: List<String>) {
        val name = args.joinToString(" ")
        val zone = zoneModule.getZone(name);
        if (zone == null) {
            sender.sendMessage(
                Component.empty()
                    .append(Component.text("There is no zone with the name "))
                    .append(Component.text(String.format("'%s'", name)))
                    .append(Component.text("."))
                    .color(NamedTextColor.RED)
            );
            return;
        }

        //Remove the zone with the given name
        zoneModule.removeZone(name);
        sender.sendMessage(
            Component.empty()
                .append(Component.text("Removed the zone named "))
                .append(Component.text(String.format("'%s'", name)))
                .append(Component.text("."))
                .color(NamedTextColor.GREEN)
        );
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String> {
        return filterTabs (zoneModule.zoneMap.keys.toList(), args)
    }
}
