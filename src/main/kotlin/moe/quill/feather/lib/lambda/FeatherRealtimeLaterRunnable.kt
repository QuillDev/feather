package moe.quill.feather.lib.lambda

import org.bukkit.scheduler.BukkitRunnable

class FeatherRealtimeLaterRunnable(private val runner: () -> Unit, delay: Long) : BukkitRunnable() {

    private val runTime = System.currentTimeMillis() + delay

    override fun run() {
        if (System.currentTimeMillis() < runTime) return;
        runner()
        cancel();
    }
}