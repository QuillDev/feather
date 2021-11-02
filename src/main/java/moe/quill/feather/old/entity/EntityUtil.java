package moe.quill.feather.old.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityUtil {

    public static void dontDespawn(Entity entity) {
        entity.getChunk().load();
        entity.getChunk().setForceLoaded(true);
        entity.setPersistent(true);

        if (entity.customName() == null) {
            entity.customName(Component.empty());
        }

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setRemoveWhenFarAway(false);
        }
    }
}
