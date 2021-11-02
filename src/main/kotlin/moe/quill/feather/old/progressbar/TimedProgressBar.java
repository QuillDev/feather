package moe.quill.feather.old.progressbar;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimedProgressBar {

    private final int ticks;
    private final Plugin plugin;

    public TimedProgressBar(Plugin plugin, int length) {
        this.ticks = length;
        this.plugin = plugin;

    }

    public BukkitTask showProgressBar(Player player,
                                      long duration,
                                      Runnable postExecution
    ) {

        return new BukkitRunnable() {
            final long startTime = System.currentTimeMillis();
            final long endTime = startTime + duration;

            @Override
            public void run() {
                final var currentTime = System.currentTimeMillis();
                final var deltaTime = currentTime - startTime;
                player.sendActionBar(buildProgressComponent(deltaTime / (double) duration));

                if (currentTime < endTime) return;
                postExecution.run();
                cancel();
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public BukkitTask showProgressBar(Player player, long duration) {
        return showProgressBar(player, duration, () -> {
        });
    }

    /**
     * Build a progress component
     *
     * @param progress to translate into ticks
     * @return the component for the current progress
     */
    public Component buildProgressComponent(double progress) {
        final int completionIndex = (int) (ticks * progress);

        //Build the text bar
        final var bar = Component.text();
        bar.append(Component.text("[").color(NamedTextColor.DARK_GRAY));

        for (int idx = 0; idx < ticks; idx++) {
            char progressComp = '|';
            bar.append(Component.text(progressComp).color((idx <= completionIndex) ? NamedTextColor.GREEN : NamedTextColor.RED));
        }

        bar.append(Component.text("]").color(NamedTextColor.DARK_GRAY));

        return bar.build();
    }
}
