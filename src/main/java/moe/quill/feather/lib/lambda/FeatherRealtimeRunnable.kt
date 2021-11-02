package moe.quill.feather.lib.lambda;

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

class FeatherRealtimeRunnable(private val lambda: () -> Unit = {}) : Runnable {

    private var task: BukkitTask? = null

    @Override
    override fun run() {
        lambda()
    }

    fun cancel() {
        task?.cancel()
    }

    fun runTaskTimer(plugin: Plugin, repeatMS: Long): BukkitTask {
        this.task = FeatherRealtimeTimerRunnable(repeatMS = repeatMS, runner = { lambda() }).runTaskTimer(plugin, 0, 1)
        return this.task!!
    }

    /**
     * Run a task x milliseconds later
     *
     * @param plugin to register this under
     * @param delay to run later
     * @return the bukkit task for this runnable
     */
    fun runTaskLater(plugin: Plugin, delay: Long): BukkitTask {
        this.task = FeatherRealtimeLaterRunnable({ lambda() }, delay = delay).runTaskLater(plugin, 1)
        return task!!;
    }
}


