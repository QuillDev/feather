package moe.quill.feather.old.selections.tool.listeners;

import moe.quill.feather.old.selections.tool.SelectToolManager;
import moe.quill.feather.old.selections.zones.Bounds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

class SelectToolListener(private val toolManager: SelectToolManager) : Listener {


    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val action = event.action
        if (!(action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK)) return;

        val slot = event.hand;
        if (slot != null && slot != EquipmentSlot.HAND) return;

        val player = event.player;
        val item = player.inventory.itemInMainHand;
        if (!item.hasItemMeta()) return;
        Bukkit.getLogger().info(toolManager.toolKey.toString())
        if (!item.itemMeta.persistentDataContainer.has(toolManager.toolKey, PersistentDataType.STRING)) return

        Bukkit.getLogger().info("JGAIIGOJAS")
        //Cancel breaking blocks
        event.isCancelled = true;

        //Get what we need from the event
        val uuid = event.player.uniqueId;
        val block = event.clickedBlock;
        if (block == null || block.type == Material.AIR) return;

        val location = block.location;

        //If the player left-clicked add them to the left click block
        if (action == Action.LEFT_CLICK_BLOCK) {

            toolManager.setLeftClick(uuid, block.location);
            sendSelectionMessage(player, location, "Point 1");
        } else {
            toolManager.setRightClick(uuid, block.location);
            sendSelectionMessage(player, location, "Point 2");
        }

        //Get the click locations on the map
        val currentLeftLoc = toolManager.getLeftClick(uuid);
        val currentRightLoc = toolManager.getRightClick(uuid);
        if (currentLeftLoc == null || currentRightLoc == null) return;

        toolManager.setSelectedBounds(uuid, Bounds(currentLeftLoc, currentRightLoc, false));
    }

    /**
     * Send the selection message to the player
     * using the given point name and the location
     *
     * @param player    to send the message to
     * @param location  to use for the print
     * @param pointName the name of the selection point
     */
    private fun sendSelectionMessage(player: Player, location: Location, pointName: String)
    {
        player.sendMessage(
            Component.text("Selected ").color(NamedTextColor.GRAY)
                .append(
                    Component.text("$pointName: ").color(NamedTextColor.GREEN)
                        .append(
                            Component.text(
                                String.format(
                                    "{ World: %s, X: %s, Y: %s, Z:%s }",
                                    location.world.name,
                                    location.blockX,
                                    location.blockY,
                                    location.blockZ
                                )
                            )
                        )
                )
        );
    }
}
