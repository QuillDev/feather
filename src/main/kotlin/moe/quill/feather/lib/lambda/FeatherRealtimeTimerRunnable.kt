package moe.quill.feather.lib.lambda

import org.bukkit.scheduler.BukkitRunnable

class FeatherRealtimeTimerRunnable(private val runner: () -> Unit, private val repeatMS: Long) : BukkitRunnable() {

    private var lastRun: Long = 0


    override fun run() {
        if (System.currentTimeMillis() - lastRun < repeatMS) return;
        runner()
        lastRun = System.currentTimeMillis();
    }
}