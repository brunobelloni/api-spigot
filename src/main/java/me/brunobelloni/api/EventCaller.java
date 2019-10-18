package me.brunobelloni.api;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static me.brunobelloni.api.Arena.playerIsInArena;
import static me.brunobelloni.api.Arena.removePlayerFromArena;

public class EventCaller implements Listener {

    private JavaPlugin plugin;

    public EventCaller(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (playerIsInArena(player)) {
            removePlayerFromArena(player);
        }
    }
}
