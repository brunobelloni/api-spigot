package me.brunobelloni.api.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Random;


public class LeatherArmorBuilder extends ItemBuilder {

    private LeatherArmorMeta lm;

    public LeatherArmorBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public LeatherArmorBuilder(Material material, int amount) {
        super(material, amount);
    }

    public LeatherArmorBuilder setColor(Color color) {
        this.lm = (LeatherArmorMeta) this.is.getItemMeta();
        this.lm.setColor(color);
        this.is.setItemMeta(this.lm);
        return this;
    }

    public LeatherArmorBuilder setColor(int red, int green, int blue) {
        this.lm = (LeatherArmorMeta) this.is.getItemMeta();
        this.lm.setColor(Color.fromRGB(red, green, blue));
        this.is.setItemMeta(this.lm);
        return this;
    }

    public LeatherArmorBuilder setRandomColor() {
        this.lm = (LeatherArmorMeta) this.is.getItemMeta();
        this.lm.setColor(Color.fromRGB(randomColor(255) + 1, randomColor(255) + 1, randomColor(255) + 1));
        this.is.setItemMeta(this.lm);
        return this;
    }

    private int randomColor(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }
}
