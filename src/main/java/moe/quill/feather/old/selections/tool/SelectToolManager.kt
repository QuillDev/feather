package moe.quill.feather.old.selections.tool

import moe.quill.feather.lib.items.ItemBuilder
import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule
import moe.quill.feather.old.selections.commands.SelectCommand
import moe.quill.feather.old.selections.tool.listeners.SelectToolListener
import moe.quill.feather.old.selections.zones.Bounds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.*

class SelectToolManager(override val plugin: FeatherPlugin) : FeatherModule {


    val toolKey = NamespacedKey(plugin, "zone-selector-tool")

    private val toolItem = ItemBuilder(Material.STICK)
        .displayName(Component.text("Selection Tool").color(NamedTextColor.GREEN))
        .applyMarkerKey(toolKey)
        .build()

    private val leftClickMap = HashMap<UUID, Location>()
    private val rightSelectionMap = HashMap<UUID, Location>()
    private val selectedBoundsMap = HashMap<UUID, Bounds>()

    init {
        registerCommand(SelectCommand(this))
        registerListener(SelectToolListener(this))
    }


    /**
     * Get the bounds for the uuid
     *
     * @param uuid to get bounds for
     * @return the bounds for this uuid
     */
    fun getBounds(uuid: UUID): Bounds? {
        return selectedBoundsMap[uuid]
    }

    /**
     * Set the selected bounds for this player
     * in the bounds map
     *
     * @param uuid   of the player
     * @param bounds to set
     */
    fun setSelectedBounds(uuid: UUID, bounds: Bounds) {
        selectedBoundsMap[uuid] = bounds
    }

    fun getLeftClick(uuid: UUID): Location? {
        return leftClickMap[uuid]
    }

    fun getRightClick(uuid: UUID): Location? {
        return rightSelectionMap[uuid]
    }

    fun setLeftClick(uuid: UUID, location: Location) {
        leftClickMap[uuid] = location
    }

    fun setRightClick(uuid: UUID, location: Location) {
        rightSelectionMap[uuid] = location
    }

    /**
     * Give the tool to the given player
     *
     * @param player the player to give the tool to
     */
    fun givePlayerTool(player: Player) {
        player.inventory.addItem(toolItem)
    }
}
