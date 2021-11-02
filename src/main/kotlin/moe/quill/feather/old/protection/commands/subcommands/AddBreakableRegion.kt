package moe.quill.feather.old.protection.commands.subcommands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.protection.ProtectionModule
import moe.quill.feather.old.selections.tool.SelectToolManager
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddBreakableRegion(
    private val protectionModule: ProtectionModule,
    private val toolManager: SelectToolManager
) : FeatherSubCommand {


    override fun getName(): String {
        return "add-break-rg";
    }

    override fun getDescription(): String {
        return "Add a breakable region to the protection manager";
    }

    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return

        val bounds = toolManager.getBounds(sender.uniqueId)

        if (bounds == null) {
            sender.sendMessage(Component.text("You must first select a region using /select tool!"))
            return
        }

        protectionModule.addArea(bounds)

    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }
}
