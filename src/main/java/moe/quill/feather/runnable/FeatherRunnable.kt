package moe.quill.feather.runnable

import org.bukkit.scheduler.BukkitRunnable

/**
 * Custom runnable implementation
 * that sits over the standard bukkit runnable
 * and allows for the usage of lambda expressions
 * as opposed to having to extend the class itself
 */
class FeatherRunnable(private val action: () -> Unit) : BukkitRunnable() {

    override fun run() {
        action()
    }
}