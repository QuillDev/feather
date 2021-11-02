package moe.quill.feather.old.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Piston;

public class BlockDataUtil {


    public static void pseudoPush(Block block) {
        if ((block.getBlockData() instanceof Piston piston)) {
            final var pistonFace = piston.getFacing();
            final var relativeBlock = block.getRelative(piston.getFacing());
            relativeBlock.getRelative(pistonFace).setBlockData(relativeBlock.getBlockData());
            piston.setExtended(true);
            block.setBlockData(piston);
        }
    }

    public static void pseudoRetract(Block block) {
        if ((block.getBlockData() instanceof Piston piston)) {
            final var pistonFace = piston.getFacing();
            final var middleMan = block.getRelative(pistonFace);
            final var targetBlock = middleMan.getRelative(pistonFace);
            middleMan.setBlockData(targetBlock.getBlockData());
            targetBlock.setType(Material.AIR);

            piston.setExtended(false);
            block.setBlockData(piston);
        }
    }

    public static void setCampfireLit(Block block, boolean lit) {
        if (block.getBlockData() instanceof Campfire campfire) {
            campfire.setLit(lit);
            block.setBlockData(campfire);
        }
    }
}
