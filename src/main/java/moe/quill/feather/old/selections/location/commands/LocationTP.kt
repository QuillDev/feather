package moe.quill.feather.old.selections.location.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.location.LocationModule
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LocationTP(private val locationModule: LocationModule) : FeatherSubCommand {

    override fun getName(): String {
        return "tp";
    }

    override fun getDescription(): String {
        return "teleport to the given location";
    }

    @Override
    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return

        if (args.size < 1) {
            sender.sendMessage(Component.text("You must specify a location name.").color(NamedTextColor.RED));
            return;
        }

        val query = args[0];

        locationModule.findLocation(query)?.let {
            sender.sendMessage(Component.text("Teleporting you to '%s'!".formatted(query)).color(NamedTextColor.GREEN));
            sender.teleportAsync(it);
        } ?: run {
            sender.sendMessage(
                Component.text("No location exists by the name '%s'!".formatted(query)).color(NamedTextColor.RED)
            )
        }
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        if (args.size == 1) {
            return filterTabs(locationModule.locations.keys.toList(), args)
        }
        return null;
    }
}
