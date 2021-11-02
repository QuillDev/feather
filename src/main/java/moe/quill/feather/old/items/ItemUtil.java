package moe.quill.feather.old.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtil {

    /**
     * Check if the given item has the given key
     *
     * @param item to compare with
     * @param key  to check against the item
     * @return whether the given item has the given marker key
     */
    public static boolean hasMarkerKey(ItemStack item, NamespacedKey key) {
        if (item == null) return false;
        if (item.getType() == Material.AIR) return false;
        final var meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }
}
