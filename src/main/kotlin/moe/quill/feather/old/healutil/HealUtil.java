package moe.quill.feather.old.healutil;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

public class HealUtil {

    public static void heal(LivingEntity entity) {
        final var attribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute == null) return;
        entity.setHealth(attribute.getValue());
    }
}
