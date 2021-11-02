package moe.quill.feather.old.selections.schematics.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.schematics.Schematic
import moe.quill.feather.old.selections.schematics.SchematicManager
import moe.quill.feather.old.selections.tool.SelectToolManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SchematicSaveCommand(private val schematicManager: SchematicManager, private val toolManager: SelectToolManager) :
    FeatherSubCommand {


    override fun getName(): String {
        return "save";
    }

    override fun getDescription(): String {
        return "save the given selection as a schematic";
    }

    override fun execute(sender: CommandSender, args: List<String>) {

        if (sender !is Player) return

        //Tell them they must specify arguments if they didn't
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("You must specify a name for this schematic").color(NamedTextColor.RED));
            return;
        }

        toolManager.getBounds(sender.uniqueId)?.let {
            val name = args.get(0);
            val schematicData = it.getSchematicData();
            schematicManager.addSchematic(Schematic(name, it, schematicData));
        } ?: run { sender.sendMessage(Component.text("You must first select your bounds using /select tool")) }
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        TODO("Not yet implemented")
    }

}
