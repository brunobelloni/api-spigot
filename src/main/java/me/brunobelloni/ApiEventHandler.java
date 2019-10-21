package me.brunobelloni;

import me.brunobelloni.api.event.Events;
import me.brunobelloni.api.event.filter.EventFilters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import static org.bukkit.Material.BLAZE_ROD;
import static org.bukkit.entity.EntityType.FIREBALL;
import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class ApiEventHandler {

    public void implement() {
        Events.subscribe(PlayerJoinEvent.class)
                .handler(e -> e.setJoinMessage("dsasda"));

        Events.merge(PlayerEvent.class, PlayerQuitEvent.class, PlayerKickEvent.class)
                .filter(e -> !e.getPlayer().isOp())
                .handler(e -> {
                    Bukkit.broadcastMessage("Player " + e.getPlayer() + " has left the server!");
                });

        Events.merge(Player.class)
                .bindEvent(PlayerDeathEvent.class, PlayerDeathEvent::getEntity)
                .bindEvent(PlayerQuitEvent.class, PlayerEvent::getPlayer)
                .handler(e -> {
                    e.getLocation().getWorld().createExplosion(e.getLocation(), 1.0f);
                });

        Events.subscribe(PlayerInteractEvent.class)
                .filter(EventFilters.ignoreCancelled())
                .filter(e -> e.getAction().equals(RIGHT_CLICK_BLOCK) || e.getAction().equals(RIGHT_CLICK_AIR))
                .filter(e -> e.getItem().getType() == BLAZE_ROD)
                .handler(e -> {
                    e.getPlayer().sendMessage("FIREBALL!");
                    e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), FIREBALL);
                });
    }
}
