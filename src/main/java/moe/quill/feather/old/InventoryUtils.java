package moe.quill.feather.old;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryUtils {

    /**
     * Set the armor to the assigned slot for the given player
     *
     * @param entity to assign hte armor to
     * @param item   to assign to this slot
     * @param slot   of armor to assign
     */
    public static void setEquipment(LivingEntity entity, ItemStack item, EquipmentSlot slot) {
        if (item == null) return;
        if (item.getType() == Material.AIR) return;


        final var equipment = entity.getEquipment();
        if (equipment == null) return;

        //Set the armor to the defined slot
        switch (slot) {
            case HEAD -> equipment.setHelmet(item);
            case CHEST -> equipment.setChestplate(item);
            case LEGS -> equipment.setLeggings(item);
            case FEET -> equipment.setBoots(item);
            case HAND -> equipment.setItemInMainHand(item);
            case OFF_HAND -> equipment.setItemInOffHand(item);
        }
    }

    /**
     * Get the contents of this inventory as a list
     *
     * @param inventory to get contents for
     * @return the contents of this list
     */
    public static List<ItemStack> getContents(Inventory inventory) {
        return convertItemArray(inventory.getContents());

    }

    public static List<ItemStack> getEntireContents(Inventory inventory) {
        final var contents = getContents(inventory);
        contents.addAll(getContents(inventory));

        if (inventory instanceof PlayerInventory playerInventory) {
            contents.addAll(convertItemArray(playerInventory.getArmorContents()));
            contents.addAll(convertItemArray(playerInventory.getExtraContents()));
        }

        return contents;
    }

    public static List<ItemStack> convertItemArray(ItemStack[] items) {
        return Arrays.stream(items)
                .filter((item) -> {
                    if (item == null) return false;
                    return !item.getType().equals(Material.AIR);
                }).collect(Collectors.toList());
    }
}
