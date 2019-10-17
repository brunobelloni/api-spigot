package me.brunobelloni.api;

import me.brunobelloni.api.event.PlayerJoinArenaEvent;
import me.brunobelloni.api.event.PlayerLeaveArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private List<UUID> playersInArena = new ArrayList<>();


    public void addPlayerToArena(Player player) {
        playersInArena.add(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new PlayerJoinArenaEvent(player));
    }

    public void removePlayerFromArena(Player player) {
        playersInArena.remove(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(player));

    }

    public Boolean playerIsInArena(Player player) {
        return playersInArena.contains(player);
    }
}
