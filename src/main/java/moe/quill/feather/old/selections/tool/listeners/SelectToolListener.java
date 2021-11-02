package moe.quill.feather.old.selections.tool.listeners;

import moe.quill.feather.old.selections.tool.SelectToolManager;
import moe.quill.feather.old.selections.zones.Bounds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class SelectToolListener implements Listener {

    private final SelectToolManager selectToolManager;

    public SelectToolListener(SelectToolManager selectToolManager) {
        this.selectToolManager = selectToolManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final var action = event.getAction();
        if (!(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK))) return;

        final var slot = event.getHand();
        if (slot != null && !slot.equals(EquipmentSlot.HAND)) return;

        final var player = event.getPlayer();
        final var item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().getPersistentDataContainer().has(selectToolManager.getToolKey(), PersistentDataType.STRING)) {
            return;
        }

        //Cancel breaking blocks
        event.setCancelled(true);

        //Get what we need from the event
        final var uuid = event.getPlayer().getUniqueId();
        final var block = event.getClickedBlock();
        if (block == null || block.getType().equals(Material.AIR)) return;

        final var location = block.getLocation();

        //If the player left-clicked add them to the left click block
        if (action.equals(Action.LEFT_CLICK_BLOCK)) {

            selectToolManager.setLeftClick(uuid, block.getLocation());
            sendSelectionMessage(player, location, "Point 1");
        } else {
            selectToolManager.setRightClick(uuid, block.getLocation());
            sendSelectionMessage(player, location, "Point 2");
        }

        //Get the click locations on the map
        final var currentLeftLoc = selectToolManager.getLeftClick(uuid);
        final var currentRightLoc = selectToolManager.getRightClick(uuid);
        if (currentLeftLoc == null || currentRightLoc == null) return;

        selectToolManager.setSelectedBounds(uuid, new Bounds(currentLeftLoc, currentRightLoc, false));
    }

    /**
     * Send the selection message to the player
     * using the given point name and the location
     *
     * @param player    to send the message to
     * @param location  to use for the print
     * @param pointName the name of the selection point
     */
    private void sendSelectionMessage(Player player, Location location, String pointName) {
        player.sendMessage(Component.text("Selected ").color(NamedTextColor.GRAY)
                .append(Component.text(pointName + ": ").color(NamedTextColor.GREEN)
                        .append(Component.text(String.format("{ World: %s, X: %s, Y: %s, Z:%s }",
                                location.getWorld().getName(),
                                location.getBlockX(),
                                location.getBlockY(),
                                location.getBlockZ()
                        )))
                ));
    }
}
