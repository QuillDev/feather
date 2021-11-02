package moe.quill.feather.old.selections.zones;

import moe.quill.feather.old.selections.types.Selection
import org.bukkit.Location

open class Zone(name: String, bounds: Bounds, infiniteY: Boolean = false) : Selection(name, bounds, infiniteY) {
    constructor(name: String, firstLocation: Location, secondLocation: Location, infiniteY: Boolean) : this(
        name,
        Bounds(firstLocation, secondLocation),
        infiniteY
    )

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): Zone {
            val name = map.getOrDefault("name", "default-name") as String;
            val bounds = map.getOrDefault("bounds", "bounds") as Bounds;
            return Zone(name, bounds);
        }
    }

}
