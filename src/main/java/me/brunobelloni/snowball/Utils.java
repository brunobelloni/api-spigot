package me.brunobelloni.snowball;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Utils {
    
    public static class Snowball {
        private static final List<UUID> playersInSnowball = new ArrayList<>();
        
        public static void addPlayerToSnowball(Player player) {
            playersInSnowball.add(player.getUniqueId());
        }
        
        public static void removePlayerToSnowball(Player player) {
            playersInSnowball.remove(player.getUniqueId());
        }
        
        public static Boolean playerIsInSnowball(Player player) {
            return playersInSnowball.contains(player.getUniqueId());
        }
    }
    
    public static class Cooldown {
        private static final HashMap<UUID, Long> itemCooldown = new HashMap<>();
        
        public static void putCooldown(Player player, Long time) {
            itemCooldown.put(player.getUniqueId(), time);
        }
        
        public static void removeCooldown(Player player) {
            itemCooldown.remove(player.getUniqueId());
        }
        
        public static Boolean isInCooldown(Player player) {
            return itemCooldown.containsKey(player.getUniqueId());
        }
    }
}
