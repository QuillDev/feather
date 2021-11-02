package moe.quill.feather.old.protection.listeners;

import moe.quill.feather.old.protection.ProtectionModule;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ManipulateBlockListener implements Listener {

    private final ProtectionModule protectionModule;

    public ManipulateBlockListener(ProtectionModule protectionModule) {
        this.protectionModule = protectionModule;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        manageProtection(event.getPlayer(), event.getBlock(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) {
        manageProtection(event.getPlayer(), event.getBlock(), event);
    }

    /**
     * Manage whether to let the player break/place the current block or not
     *
     * @param player      to check
     * @param block       to check if it's breakable
     * @param cancellable to cancel if we cannot break it
     */
    private void manageProtection(Player player, Block block, Cancellable cancellable) {
        if (player.isOp() || player.getGameMode().equals(GameMode.CREATIVE)) return;

        final var blockLocation = block.getLocation().toBlockLocation();
        if (protectionModule.isExempt(blockLocation)) return;
        cancellable.setCancelled(true);
    }
}
