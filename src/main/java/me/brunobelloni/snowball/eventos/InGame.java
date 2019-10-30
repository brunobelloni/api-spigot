package me.brunobelloni.snowball.eventos;

import me.brunobelloni.api.event.Events;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import static me.brunobelloni.snowball.Utils.Cooldown.putCooldown;
import static me.brunobelloni.snowball.Utils.Cooldown.removeCooldown;
import static me.brunobelloni.snowball.Utils.Snowball.playerIsInSnowball;
import static me.brunobelloni.snowball.Utils.Snowball.removePlayerToSnowball;
import static me.brunobelloni.snowball.Utils.SnowballItens.cooldownItem;
import static me.brunobelloni.snowball.Utils.SnowballItens.snowballItem;
import static me.brunobelloni.snowball.Utils.SnowballLocation.getSnowballLocation;
import static org.bukkit.Sound.ENDERMAN_TELEPORT;
import static org.bukkit.Sound.NOTE_PLING;

public class InGame {

    private static Long cooldownTime;
    private static JavaPlugin plugin;
    private static BukkitScheduler scheduler;

    public InGame(JavaPlugin plugin) {
        cooldownTime = 10L;
        InGame.plugin = plugin;
        scheduler = plugin.getServer().getScheduler();
    }

    public void execute() {
        /**
         * Bloqueia que o player drope o item
         */
        Events.subscribe(PlayerDropItemEvent.class)
                .filter(e -> playerIsInSnowball(e.getPlayer()))
                .filter(e -> {
                    ItemStack material = e.getItemDrop().getItemStack();
                    return material.equals(cooldownItem) || material.equals(snowballItem);
                })
                .handler(e -> {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Você não pode dropar esse item!");
                });

        /**
         * Evento para tratar o cooldown da Snowball
         */
        Events.subscribe(ProjectileLaunchEvent.class)
                .filter(e -> e.getEntity().getShooter() instanceof Player)
                .filter(e -> e.getEntity() instanceof Snowball)
                .filter(e -> playerIsInSnowball((Player) e.getEntity()))
                .handler(e -> {
                    Player shooter = (Player) e.getEntity().getShooter();

                    /**
                     * Adiciona o item de cooldown (async) para o player
                     */
                    scheduler.runTaskAsynchronously(plugin, () -> shooter.setItemInHand(cooldownItem));

                    /**
                     * Bota um cooldown de 5 segundos no player
                     */
                    putCooldown(shooter, cooldownTime);

                    /**
                     * Agenda uma Tarefa para remove o cooldown do player depois de 5 segundos.
                     */
                    scheduler.runTaskLaterAsynchronously(plugin, () -> {
                        removeCooldown(shooter);
                        shooter.getInventory().removeItem(cooldownItem);
                        shooter.getInventory().setItem(0, snowballItem);
                        shooter.playSound(shooter.getLocation(), NOTE_PLING, 5.0F, 1.0F);
                    }, cooldownTime * 20L);
                });

        /**
         * Evento para tratar quando um player (shooter) atinge outro player (hitted)
         */
        Events.subscribe(EntityDamageByEntityEvent.class)
                .filter(e -> e.getEntity() instanceof Player)
                .filter(e -> e.getDamager() instanceof Snowball)
                .filter(e -> ((Snowball) e.getDamager()).getShooter() instanceof Player)
                .filter(e -> playerIsInSnowball((Player) e.getEntity()))
                .handler(e -> {
                    Player shooter = (Player) ((Snowball) e.getDamager()).getShooter();
                    Player hitted = (Player) e.getEntity();

                    if (playerIsInSnowball(shooter) && playerIsInSnowball(hitted)) {
                        hitted.damage(20, shooter);
                        shooter.setLevel(shooter.getLevel() + 1);

                        hitted.playSound(hitted.getLocation(), ENDERMAN_TELEPORT, 5.0F, 1.0F);
                        shooter.playSound(shooter.getLocation(), ENDERMAN_TELEPORT, 5.0F, 1.0F);
                    }
                });

        /**
         * Bloqueia comandos na arena
         */
        Events.subscribe(PlayerCommandPreprocessEvent.class)
                .filter(e -> playerIsInSnowball(e.getPlayer()))
                .handler(e -> {
                    String cmd = e.getMessage().substring(1);

                    if (!cmd.startsWith("snowball")) {
                        e.getPlayer().sendMessage(ChatColor.RED + "Digite /snowball para sair!");
                        e.setCancelled(true);
                    }
                });

        /**
         * Altera o spawn location quando o player estiver no Snowball
         */
        Events.subscribe(PlayerRespawnEvent.class)
                .filter(e -> playerIsInSnowball(e.getPlayer()))
                .handler(e -> {
                    e.setRespawnLocation(getSnowballLocation());
                });

        /**
         * Mantem level quando morre
         * Auto-respawn
         */
        Events.subscribe(PlayerDeathEvent.class)
                .filter(e -> playerIsInSnowball(e.getEntity()))
                .handler(e -> {
                    e.setKeepLevel(true);
                    e.setDeathMessage("");
                    e.getKeepInventory();
                    e.getEntity().spigot().respawn();
                });

        /**
         * Remove o player da arena quando ele sair do servidor
         */
        Events.merge(PlayerEvent.class, PlayerQuitEvent.class, PlayerKickEvent.class)
                .filter(e -> playerIsInSnowball(e.getPlayer()))
                .handler(e -> removePlayerToSnowball(e.getPlayer()));
    }
}
