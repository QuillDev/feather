package moe.quill.feather.old.selections.schematics.commands;

import moe.quill.feather.lib.command.FeatherCommand
import moe.quill.feather.old.selections.schematics.SchematicManager
import moe.quill.feather.old.selections.tool.SelectToolManager
import org.bukkit.command.CommandSender

class SchematicCommand(schematicManager: SchematicManager, toolManager: SelectToolManager) :
    FeatherCommand("schem", "Gives help for schematics") {

    init {
        registerSubCommand(
            SchematicSaveCommand(schematicManager, toolManager),
            SchematicsPasteCommand(schematicManager),
            SchematicMatchesCommand(schematicManager),
            SchematicRemoveCommand(schematicManager)
        )
    }

    override fun execute(sender: CommandSender, args: List<String>) {

    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }
}
