package me.brunobelloni;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.RED;
import static org.bukkit.Effect.EXPLOSION_LARGE;
import static org.bukkit.Effect.LARGE_SMOKE;
import static org.bukkit.Material.STICK;
import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL;

public class Event implements Listener {

    private static List<UUID> playersNoAr = new ArrayList<UUID>();

    @EventHandler
    public void onPlayerClickStickEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        Material itemInHand = p.getItemInHand().getType();

        if(itemInHand.equals(STICK)) {
            if (action.equals(RIGHT_CLICK_AIR) || action.equals(RIGHT_CLICK_BLOCK)) {
                if(playersNoAr.contains(p.getUniqueId())) {
                    p.sendMessage(RED + "Você já está voando!");
                    return;
                }

                Vector vector = new Vector(1, 500, 1);
                p.setVelocity(vector);
                playersNoAr.add(p.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            DamageCause damageCause = e.getCause();

            if (playersNoAr.contains(p.getUniqueId())) {
                if(damageCause.equals(FALL)) {
                    e.setCancelled(true);
                    e.setDamage(0);
                    p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
                    playersNoAr.remove(p.getUniqueId());
                }
            }
        }
    }
}
