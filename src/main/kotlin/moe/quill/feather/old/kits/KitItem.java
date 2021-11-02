package moe.quill.feather.old.kits;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitItem implements Comparable<KitItem> {

    private ItemStack item;
    private int priority;

    public KitItem(ItemStack item, int priority) {
        this.item = item;
        this.priority = priority;
    }

    public KitItem(ItemStack item) {
        this(item, 0);
    }

    public int getPriority() {
        return priority;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(@NotNull KitItem o) {
        return o.priority - priority;
    }

}
