package moe.quill.feather.old.fireworks;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;

public class FireworkUtil {

    public static void spawnFirework(Location location, FireworkEffect effect) {
        final var firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        final var meta = firework.getFireworkMeta();
        meta.addEffect(effect);
        meta.setPower(1);
        firework.setFireworkMeta(meta);
    }
}
