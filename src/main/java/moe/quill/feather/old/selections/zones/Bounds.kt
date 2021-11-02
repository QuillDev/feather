package moe.quill.feather.old.selections.zones

import moe.quill.feather.old.Filter
import moe.quill.feather.old.selections.schematics.SchematicData
import moe.quill.feather.old.selections.zones.utils.LocationUtils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.configuration.serialization.ConfigurationSerializable

open class Bounds(val firstLocation: Location, val secondLocation: Location, var infiniteY: Boolean = false) :
    ConfigurationSerializable {

    val minLocation: Location = LocationUtils.getMinLocation(firstLocation, secondLocation)
    val maxLocation: Location = LocationUtils.getMaxLocation(firstLocation, secondLocation)

    /**
     * Get whether the given location is within the given bounds
     *
     * @param location to use for checking whether it's in the bounds
     * @return whether the location is within the bounds
     */
    fun isInBounds(location: Location): Boolean {
        return LocationUtils.isWithinBounds(minLocation, maxLocation, location, !infiniteY)
    }

    /**
     * Get all the blocks within the bounds of this zone
     *
     * @return the blocks within the bounds of this zone
     */
    fun getBlocksInBounds(): List<Block> {
        val blocks = ArrayList<Block>()
        val world = minLocation.world

        for (x in minLocation.blockX..maxLocation.blockX) {
            for (y in minLocation.blockY..maxLocation.blockY) {
                for (z in minLocation.blockZ..maxLocation.blockZ) {
                    blocks.add(Location(world, x.toDouble(), y.toDouble(), z.toDouble()).block)
                }
            }
        }

        return blocks
    }

    fun deleteBlocks() {
        getBlocksInBounds().forEach { block ->
            block.drops.clear()
            block.type = Material.AIR
        }
    }

    /**
     * Get the schematic data for these bounds
     *
     * @return the schematic data for these bounds
     */
    fun getSchematicData(): SchematicData {
        val layerList = ArrayList<List<List<BlockData>>>()
        val world = minLocation.world

        //Iterate through the xyz coords and check if it's within the bounds
        for (y in minLocation.blockY..maxLocation.blockY) {
            val yList = ArrayList<List<BlockData>>()
            for (x in minLocation.blockX..maxLocation.blockX) {
                val xList = ArrayList<BlockData>()
                for (z in minLocation.blockZ..maxLocation.blockZ) {
                    xList.add(Location(world, x.toDouble(), y.toDouble(), z.toDouble()).block.blockData)
                }
                yList.add(xList)
            }
            layerList.add(yList)
        }

        return SchematicData(layerList)
    }

    fun replace(target: Material, replacement: Material) {
        getBlocksInBounds().forEach { block ->
            if (block.type != target) return
            try {
                block.type = replacement
            } catch (ignored: Exception) {
            }
        }
    }

    /**
     * Get blocks using the given filter
     *
     * @param filter      to use
     * @param filterTypes the types to use in the filter
     * @return the collection of blocks using the given filter
     */
    fun filteredBlocks(filter: Filter, vararg filterTypes: Material): List<Block> {
        val types = filterTypes.toList()

        return getBlocksInBounds().filter { block ->
            when (filter) {
                Filter.WHITELIST -> types.contains(block.type)
                Filter.BLACKLIST -> !types.contains(block.type)
            }
        }
    }

    /**
     * Get a random location in the bounds
     *
     * @return the random location in the bounds
     */
    fun getRandomLocation(): Location {
        val deltaX = maxLocation.x - minLocation.x
        val deltaZ = maxLocation.z - minLocation.z

        val xOffset = Math.random() * deltaX
        val zOffset = Math.random() * deltaZ

        return Location(
            minLocation.world,
            minLocation.x + xOffset,
            minLocation.y + 1,
            minLocation.z + zOffset
        )

    }

    fun getCenter(): Location {
        return Location(
            minLocation.world,
            minLocation.x + ((maxLocation.x - minLocation.x) / 2.0),
            minLocation.y + ((maxLocation.y - minLocation.y) / 2.0),
            minLocation.z + ((maxLocation.z - minLocation.z) / 2.0)
        )
    }


    //Bukkit's serialization stuff
    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): Bounds {
            val world = Bukkit.getServer().worlds[0]
            val fallbackLocation = Location(world, 0.toDouble(), 0.toDouble(), 0.toDouble())
            if (world == null) return Bounds(fallbackLocation, fallbackLocation, false)
            val firstLocation = map.getOrDefault("first-location", fallbackLocation) as Location
            val secondLocation = map.getOrDefault("second-location", fallbackLocation) as Location
            val infiniteY = map.getOrDefault("infinite-y", false) as Boolean
            return Bounds(firstLocation, secondLocation, infiniteY)
        }
    }


    @Override
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "first-location" to firstLocation,
            "second-location" to secondLocation,
            "infinite-y" to infiniteY
        )
    }

}
