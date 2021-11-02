package moe.quill.feather.old.countdown;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

public class CountdownUtil {

    private static Plugin plugin;

    public static void init(Plugin plugin) {
        CountdownUtil.plugin = plugin;
    }

    /**
     * Start a countdown for the given player for hte given duration
     *
     * @param players      to process the countdown for
     * @param duration     to have the countdown for
     * @param subtitle     to have under the countdown
     * @param onCompletion what to run on completion
     */
    public static BukkitTask countdown(
            Collection<? extends Player> players,
            long duration,
            Component subtitle,
            Runnable onCompletion,
            Function<Double, TextColor> colorSupplier,
            @Nullable Supplier<Boolean> cancelSupplier
    ) {
        return new BukkitRunnable() {
            final long endTime = System.currentTimeMillis() + duration;

            @Override
            public void run() {

                if (cancelSupplier != null) {
                    if (cancelSupplier.get()) {
                        cancel();
                        return;
                    }
                }


                final var curTime = System.currentTimeMillis();
                final var timeLeft = endTime - curTime;
                final var progress = timeLeft / (double) duration;
                if (timeLeft <= 0) {
                    onCompletion.run();
                    cancel();
                    return;
                }

                final var secondsLeft = (long) Math.ceil((timeLeft / 1000.));
                players.forEach((player) -> {
                    player.showTitle(
                            Title.title(
                                    Component.text(secondsLeft).color(colorSupplier.apply(progress)),
                                    subtitle,
                                    Title.Times.of(Duration.ZERO, Duration.of(1000, ChronoUnit.MILLIS), Duration.ZERO)
                            )
                    );
                });

            }
        }.runTaskTimer(plugin, 0, 10);
    }

    /**
     * Start a countdown for the given player for hte given duration
     *
     * @param players      to process the countdown for
     * @param duration     to have the countdown for
     * @param subtitle     to have under the countdown
     * @param onCompletion what to run on completion
     */
    public static BukkitTask countdown(
            Collection<? extends Player> players,
            long duration,
            Component subtitle,
            Runnable onCompletion,
            Function<Double, TextColor> colorSupplier
    ) {
        return countdown(players, duration, subtitle, onCompletion, colorSupplier, () -> false);
    }


    public static void countdown(Player player, long duration, Component subtitle, Runnable onCompletion, Function<Double, TextColor> colorSupplier) {
        countdown(Collections.singletonList(player), duration, subtitle, onCompletion, colorSupplier);
    }

    public static void countdown(Player player, long duration, Function<Double, TextColor> colorSupplier) {
        countdown(player, duration, Component.empty(), () -> {
        }, colorSupplier);
    }

    public static void countdown(Player player, long duration, Runnable runnable) {
        countdown(player, duration, Component.empty(), runnable, (_progress) -> NamedTextColor.WHITE);
    }

    public static void countdown(Player player, long duration, Component subtitle) {
        countdown(player, duration, subtitle, () -> {
        }, (_progress) -> NamedTextColor.WHITE);
    }

    public static void countdown(Player player, long duration) {
        countdown(player, duration, Component.empty());
    }

    public static TextColor getProgressColor(double progress) {
        NamedTextColor color;
        if (progress >= .8) {
            color = NamedTextColor.GREEN;
        } else if (progress >= .5) {
            color = NamedTextColor.YELLOW;
        } else if (progress >= .35) {
            color = NamedTextColor.GOLD;
        } else {
            color = NamedTextColor.RED;
        }
        return color;
    }
}
