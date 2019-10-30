package me.brunobelloni.snowball;

import me.brunobelloni.api.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static me.brunobelloni.snowball.Utils.SnowballItens.snowballItem;
import static me.brunobelloni.snowball.Utils.SnowballLocation.getLeaveLocation;
import static me.brunobelloni.snowball.Utils.SnowballLocation.getSnowballLocation;

public class Utils {
    
    public static class Snowball {
        private static final List<UUID> playersInSnowball = new ArrayList<>();
        
        public static void addPlayerToSnowball(Player player) {
            if (getLeaveLocation() == null || getSnowballLocation() == null) {
                player.sendMessage(ChatColor.RED + "É preciso definir o spawn do Snowball");
                return;
            }
            player.setLevel(0);
            player.getInventory().clear();
            player.teleport(getSnowballLocation());
            player.getInventory().addItem(snowballItem);
            player.sendMessage(ChatColor.GREEN + "Você entrou no SNOWBALL!");

//            ActionBar actionBar = new ActionBar("Hello world");
//            actionBar.send(player);

            playersInSnowball.add(player.getUniqueId());
        }

        public static void removePlayerToSnowball(Player player) {
            if (getLeaveLocation() == null || getSnowballLocation() == null) {
                player.sendMessage(ChatColor.RED + "É preciso definir o spawn do Snowball");
                return;
            }
            player.setLevel(0);
            player.getInventory().clear();
            player.teleport(getLeaveLocation());
            player.sendMessage(ChatColor.RED + "Você saiu o SNOWBALL!");
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

    public static class SnowballLocation {
        private static Location snowballLocation;
        private static Location leaveLocation;

        public static Location getSnowballLocation() {
            return snowballLocation;
        }

        public static void setSnowballLocation(Location snowballLocation) {
            SnowballLocation.snowballLocation = snowballLocation;
        }

        public static Location getLeaveLocation() {
            return leaveLocation;
        }

        public static void setLeaveLocation(Location leaveLocation) {
            SnowballLocation.leaveLocation = leaveLocation;
        }
    }

    public static class SnowballItens {
        public static final ItemStack snowballItem = new ItemBuilder(Material.SNOW_BALL)
                .setDisplayName(ChatColor.GREEN + "Snowball")
                .build();

        public static final ItemStack cooldownItem = new ItemBuilder(Material.SLIME_BALL)
                .setDisplayName(ChatColor.RED + "Espere o tempo de recarga!")
                .build();
    }
}
