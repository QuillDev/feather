package moe.quill.feather.old.selections.schematics;

import moe.quill.feather.old.Filter
import moe.quill.feather.old.entity.EntityUtil
import moe.quill.feather.old.selections.zones.Bounds
import moe.quill.feather.old.selections.zones.Zone
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.FallingBlock

class Schematic(
    name: String, private val bounds: Bounds, private val schemData: SchematicData
) : Zone(name, bounds) {
    constructor(
        name: String,
        firstLocation: Location,
        secondLocation: Location,
        infiniteY: Boolean,
        schemData: SchematicData
    ) : this(name, Bounds(firstLocation, secondLocation, infiniteY), schemData)

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): Schematic {
            val name = map.getOrDefault("name", "default-name") as String;
            val bounds = map.getOrDefault("bounds", null) as Bounds;
            val schematicData = map.getOrDefault("block-data", null) as SchematicData;
            return Schematic(name, bounds, schematicData);

        }
    }


    /**
     * Check if the blocks at the current location match
     * types and orientation of the given schematic
     *
     * @param location to check matching with
     */
    fun locationMatchesSchematic(location: Location): Boolean {

        val schematicTypes = schemData.blockData
        for (y in 0..schematicTypes.size) {
            val xList = schematicTypes[y];
            for (x in 0..xList.size) {
                val zList = xList[x];
                for (z in 0..zList.size) {
                    val typeAtLocation = location.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block.type;
                    val currentType = zList[z].material;
                    if (typeAtLocation == currentType) continue;
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the bounds of this object relative to the given location
     *
     * @param location to get the bounds relative to
     * @return the bounds relative to the given location
     */
    fun getRelativeBounds(location: Location): Bounds {
        val yOffset = maxLocation.blockY - minLocation.blockY;
        val xOffset = maxLocation.blockX - minLocation.blockX;
        val zOffset = maxLocation.blockZ - minLocation.blockZ;

        val relativeMax = location.clone().add(xOffset.toDouble(), yOffset.toDouble(), zOffset.toDouble());

        return Bounds(location, relativeMax, bounds.infiniteY);
    }

    fun locationMatchPercent(location: Location): Double {
        var totalBlocks = 0;
        var matchingBlocks = 0;

        val schematicTypes = schemData.blockData
        for (y in 0..schematicTypes.size) {
            val xList = schematicTypes[y]
            for (x in 0..xList.size) {
                val zList = xList[x];
                for (z in 0..zList.size) {
                    val typeAtLocation = location.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block.type;
                    val currentType = zList[z];
                    //If the type matches increment matching blocks
                    if (typeAtLocation == currentType.material) {
                        matchingBlocks++;
                    }
                    totalBlocks++;
                }
            }
        }

        if (totalBlocks == 0) return 1.0;
        return matchingBlocks.toDouble() / totalBlocks.toDouble();
    }

    /**
     * Paste the schematic at the given location
     *
     * @param location to check equality with
     */
    fun paste(location: Location) {
        val schematicTypes = schemData.blockData;
        for (y in 0..schematicTypes.size) {
            val xList = schematicTypes[y];
            for (x in 0..xList.size) {
                val zList = xList[x];
                for (z in 0..zList.size) {
                    location.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block.blockData = zList.get(z);
                }
            }
        }
    }

    fun drop(): Collection<FallingBlock> {
        //Cache the blocks, so we know hte types when we try to drop them
        val blockCache = HashMap<Location, BlockData>();
        paste(minLocation);

        filteredBlocks(
            Filter.BLACKLIST,
            Material.AIR,
            Material.BARRIER
        ).forEach { block -> blockCache[block.location] = block.blockData }
        deleteBlocks();

        val entities = ArrayList<FallingBlock>();
        //Spawn falling blocks for a cool effect
        blockCache.forEach { (location, blockData) ->
            try {
                val block = location.world.spawnFallingBlock(location.clone().add(0.5, 0.0, 0.5), blockData);
                EntityUtil.dontDespawn(block);
                entities.add(block);
            } catch (ignored: IllegalArgumentException) {

            }
            Bukkit.getLogger().info("Dropping " + blockCache.size + " blocks.");

            return entities;
        }

        return arrayListOf()
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "bounds" to bounds,
            "block-data" to schemData

        )
    }
}


