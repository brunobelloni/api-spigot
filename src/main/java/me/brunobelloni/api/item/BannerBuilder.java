package me.brunobelloni.api.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerBuilder extends ItemBuilder {

    private BannerMeta bm;

    public BannerBuilder(ItemStack itemStack) {
        super(itemStack);
    }
}

