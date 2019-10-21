package me.brunobelloni.api.firework;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkBuilder {

    private Firework f;
    private FireworkMeta fm;

    public FireworkBuilder(Location loc, FireworkEffect.Type type, Color color, Color fadeColor, boolean flicker, boolean trail, int power) {
        this.f = (Firework) loc.getWorld().spawn(loc, Firework.class);
        FireworkEffect effect = FireworkEffect.builder()
               .withColor(color)
               .flicker(flicker)
               .trail(trail)
               .withFade(fadeColor)
               .with(type)
               .build();
        this.fm = this.f.getFireworkMeta();
        this.fm.addEffect(effect);
        this.fm.setPower(power);
    }

    public FireworkBuilder(Location loc, FireworkEffect.Type type, Color color, boolean flicker, boolean trail, int power) {
        this.f = (Firework) loc.getWorld().spawn(loc, Firework.class);
        FireworkEffect effect = FireworkEffect.builder()
               .withColor(color)
               .flicker(flicker)
               .trail(trail)
               .with(type)
               .build();
        this.fm = this.f.getFireworkMeta();
        this.fm.addEffect(effect);
        this.fm.setPower(power);
    }

    public FireworkBuilder build() {
        this.f.setFireworkMeta(this.fm);
        return null;
    }
}
