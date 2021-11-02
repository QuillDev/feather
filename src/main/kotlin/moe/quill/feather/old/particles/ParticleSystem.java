package moe.quill.feather.old.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleSystem {

    private final Plugin plugin;

    public ParticleSystem(Plugin plugin) {
        this.plugin = plugin;
    }

    public void drawParticleLine(Color color, Location start, Location end, long ticksPerUpdate, double distancePerUpdate) {

        new BukkitRunnable() {

            final Vector startVec = start.toVector();
            final Vector endVec = end.toVector();

            final double maxDistance = start.distance(end);
            double distance = 0;

            final Vector accumVec = startVec.clone();
            final Vector deltaVec = endVec.clone().subtract(startVec).normalize().multiply(distancePerUpdate);

            final World world = start.getWorld();

            @Override
            public void run() {
                //If we passed the distance return
                if (distance >= maxDistance) {
                    cancel();
                    return;
                }

                world.spawnParticle(Particle.REDSTONE, accumVec.toLocation(world), 1, new Particle.DustOptions(color, 2));

                accumVec.add(deltaVec);
                distance += distancePerUpdate;
            }
        }.runTaskTimer(plugin, 0, ticksPerUpdate);
    }
}
