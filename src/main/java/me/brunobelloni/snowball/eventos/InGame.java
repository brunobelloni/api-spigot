package me.brunobelloni.snowball.eventos;

import me.brunobelloni.api.event.Events;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class InGame implements AbstractGame {
    
    @Override
    public void execute() {
        
        /**
         * Evento para tratar o cooldown da Snowball
         */
        Events.subscribe(ProjectileLaunchEvent.class)
               .filter(e -> e.getEntity().getShooter() instanceof Player)
               .filter(e -> e.getEntity() instanceof Snowball)
               .filter(e -> {
                   if (true) {
                       /// verify if player is in cooldown map
                       ((Player) (e.getEntity().getShooter())).sendMessage(ChatColor.RED + "Espere o tempo de recarga!");
                       return false;
                   }
                   return true;
               })
               .handler(e -> {
                   Player shooter = (Player) e.getEntity().getShooter();
                   /// put player in cooldown map
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
                   
                   hitted.teleport(shooter.getLocation());
                   shooter.teleport(hitted.getLocation());
                   
                   hitted.playSound(hitted.getLocation(), Sound.ENDERMAN_TELEPORT, 5.0F, 1.0F);
                   shooter.playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 5.0F, 1.0F);
               });
    }
}
