package moe.quill.feather.old.selections.schematics.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.schematics.SchematicManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SchematicsPasteCommand(private val schematicManager: SchematicManager) : FeatherSubCommand {

    override fun getName(): String {
        return "paste";
    }

    override fun getDescription(): String {
        return "Paste the schematic at your current location.";
    }

    @Override
    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("You must specify the name of the schematic you wish to load"));
            return;
        }

        val name = args[0];
        val schematic = schematicManager.getSchematic(name);
        if (schematic == null) {
            sender.sendMessage(
                Component.text("No schematic exists with the name ")
                    .append(Component.text(name))
                    .append(Component.text("."))
                    .color(NamedTextColor.RED)
            );
            return;
        }

        schematic.paste(sender.location);
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }

}
