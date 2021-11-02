package moe.quill.feather.old.selections.schematics.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.schematics.SchematicManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

class SchematicRemoveCommand(private val schematicManager: SchematicManager) : FeatherSubCommand {

    override fun getName(): String {
        return "remove";
    }

    override fun getDescription(): String {
        return "removes the given schematic";
    }


    @Override
    override fun execute(sender: CommandSender, args: List<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("You must provide a schematic name").color(NamedTextColor.RED));
            return;
        }

        val success = schematicManager.removeSchematic(args[0]);

        sender.sendMessage(
            Component.text(if (success) "Successfully removed the schematic " else "Failed to remove the schematic ")
                .append(Component.text(args.get(0)))
                .append(Component.text("."))
                .color(
                    if (success) NamedTextColor.GREEN else NamedTextColor.RED
                )
        );
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }

}
