package me.brunobelloni.api.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullBuilder extends ItemBuilder {

    private SkullMeta sm;

    public SkullBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public SkullBuilder(Material material, int amount) {
        super(material, amount);
    }

    public SkullBuilder setOwner(String owner) {
        this.sm = (SkullMeta) this.is.getItemMeta();
        this.sm.setOwner(owner);
        this.is.setItemMeta(this.sm);
        return this;
    }
}
