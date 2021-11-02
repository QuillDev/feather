package moe.quill.feather.old.selections.commands;

import moe.quill.feather.lib.command.FeatherSubCommand
import moe.quill.feather.old.selections.tool.SelectToolManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SelectToolCommand(private val toolManager: SelectToolManager) : FeatherSubCommand {

    override fun getName(): String {
        return "tool";
    }


    override fun getDescription(): String {
        return "gets the selection tool.";
    }

    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) return
        toolManager.givePlayerTool(sender);
        sender.sendMessage(
            Component.text("Here's your ").color(NamedTextColor.GRAY)
                .append(Component.text("Selection Tool").color(NamedTextColor.GREEN))
                .append(Component.text(".").color(NamedTextColor.GRAY))
        );
    }

    override fun tabComplete(sender: CommandSender, args: List<String>): List<String>? {
        return null
    }
}
