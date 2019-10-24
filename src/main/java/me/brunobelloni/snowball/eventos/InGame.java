package me.brunobelloni.snowball.eventos;

import me.brunobelloni.api.event.Events;
import me.brunobelloni.api.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static me.brunobelloni.snowball.Utils.Cooldown.putCooldown;
import static me.brunobelloni.snowball.Utils.Cooldown.removeCooldown;
import static org.bukkit.Sound.ENDERMAN_TELEPORT;
import static org.bukkit.Sound.NOTE_PLING;

public class InGame implements AbstractGame {

    private static final Long cooldownTime = 5L;
    private static final ItemStack snowballItem = new ItemBuilder(Material.SNOW_BALL).setDisplayName(ChatColor.GREEN + "Snowball").build();
    private static final ItemStack cooldownItem = new ItemBuilder(Material.SLIME_BALL).setDisplayName(ChatColor.RED + "Espere o tempo de recarga!").build();
    private static JavaPlugin plugin;

    public InGame(JavaPlugin plugin) {
        InGame.plugin = plugin;
    }

    @Override
    public void execute() {
        /**
         * Bloqueia que o player drope o item
         */
        Events.subscribe(PlayerDropItemEvent.class)
                .filter(e -> {
                    ItemStack material = e.getItemDrop().getItemStack();
                    return material.equals(cooldownItem) || material.equals(snowballItem);
                })
                .handler(e -> {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Você não pode droppar esse item!");
                });

        /**
         * Evento para tratar o cooldown da Snowball
         */
        Events.subscribe(ProjectileLaunchEvent.class)
               .filter(e -> e.getEntity().getShooter() instanceof Player)
               .filter(e -> e.getEntity() instanceof Snowball)
               .handler(e -> {
                   Player shooter = (Player) e.getEntity().getShooter();

                   /**
                    * Adiciona o item de cooldown (async) para o player
                    */
                   plugin.getServer().getScheduler()
                           .runTaskAsynchronously(plugin, () -> {
                               shooter.setItemInHand(cooldownItem);
                           });

                   /**
                    * Bota um cooldown de 5 segundos no player
                    */
                   putCooldown(shooter, cooldownTime);

                   /**
                    * Agenda uma Tarefa para remove o cooldown do player depois de 5 segundos.
                    */
                   plugin.getServer().getScheduler()
                           .runTaskLaterAsynchronously(plugin, () -> {
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
               .handler(e -> {
                   Player shooter = (Player) ((Snowball) e.getDamager()).getShooter();
                   Player hitted = (Player) e.getEntity();

                   Location hittedLocation = hitted.getLocation();
                   Location shooterLocation = shooter.getLocation();

                   /**
                    * Troca a posição dos jogadores
                    */
                   hitted.teleport(shooterLocation);
                   shooter.teleport(hittedLocation);

                   /**
                    * Reproduz {@link Sound.ENDERMAN_TELEPORT}
                    */
                   hitted.playSound(hitted.getLocation(), ENDERMAN_TELEPORT, 5.0F, 1.0F);
                   shooter.playSound(shooter.getLocation(), ENDERMAN_TELEPORT, 5.0F, 1.0F);
               });
    }
}
