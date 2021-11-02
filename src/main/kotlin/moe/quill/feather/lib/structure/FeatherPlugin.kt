package moe.quill.feather.lib.structure

import moe.quill.feather.lib.structure.api.FeatherPluginChassis
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import org.bukkit.plugin.java.JavaPlugin

open class FeatherPlugin : JavaPlugin(), FeatherPluginChassis, ForwardingAudience {

    override fun audiences(): MutableIterable<Audience> {
        return server.onlinePlayers
    }

    override fun plugin(): JavaPlugin {
        return this
    }
}