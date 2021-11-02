package moe.quill.feather.lib.lambda

import org.bukkit.scheduler.BukkitRunnable

/**
 * Class that allows for lambdas to be used in bukkit runnable
 * without having to do weird stuff with classes
 */
class FeatherRunnable(private val action: (BukkitRunnable) -> Unit = {}) : BukkitRunnable() {
    override fun run() {
        action(this)
    }
}