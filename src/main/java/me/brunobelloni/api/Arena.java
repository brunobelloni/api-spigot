package me.brunobelloni.api;

import me.brunobelloni.api.event.PlayerJoinArenaEvent;
import me.brunobelloni.api.event.PlayerLeaveArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.brunobelloni.api.Configuration.*;

public class Arena {

    private static List<UUID> playersInArena = new ArrayList<>();

    public static void addPlayerToArena(Player player) {
        if (getArenaSpawn() == null || getCommomSpawn() == null || getArenaLoc1() == null || getArenaLoc2() == null) {
            player.sendMessage(ChatColor.RED + "É preciso setar todos os locais!");
            return;
        }

        if (playerIsInArena(player)) {
            player.sendMessage(ChatColor.RED + "Você já está na arena!");
            return;
        }

        playersInArena.add(player.getUniqueId());
        player.teleport(getArenaSpawn());
        player.sendMessage(ChatColor.GREEN + "Você entrou na arena!");
        Bukkit.getPluginManager().callEvent(new PlayerJoinArenaEvent(player));
    }

    public static void removePlayerFromArena(Player player) {
        if (getArenaSpawn() == null || getCommomSpawn() == null || getArenaLoc1() == null || getArenaLoc2() == null) {
            player.sendMessage(ChatColor.RED + "É preciso setar todos os locais!");
            return;
        }

        if (!playerIsInArena(player)) {
            player.sendMessage(ChatColor.RED + "É preciso estar na arena para sair dela!");
            return;
        }

        playersInArena.remove(player.getUniqueId());
        player.teleport(getCommomSpawn());
        player.sendMessage(ChatColor.RED + "Você saiu da arena!");
        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(player));
    }

    public static Boolean playerIsInArena(Player player) {
        return playersInArena.contains(player.getUniqueId());
    }
}
