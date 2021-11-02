package moe.quill.feather.old.selections.types;

import moe.quill.feather.old.selections.zones.Bounds
import org.bukkit.configuration.serialization.ConfigurationSerializable


open class Selection(val name: String, private val bounds: Bounds, infiniteY: Boolean = false) :
    Bounds(bounds.minLocation, bounds.maxLocation, infiniteY),
    ConfigurationSerializable {

    //Bukkit's Serialization



    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "bounds" to bounds
        )
    }

    override fun toString(): String
    {
        val  center = getCenter();
        return String.format(
            "World: %s, X: %s, Y: %s, Z:%s",
            center.world.name,
            center.blockX,
            center.blockY,
            center.blockZ
        );
    }
}
