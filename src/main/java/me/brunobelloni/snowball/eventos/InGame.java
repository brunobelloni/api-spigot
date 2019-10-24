package me.brunobelloni.snowball.eventos;

import me.brunobelloni.api.event.Events;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class InGame implements AbstractGame {
    
    @Override
    public void execute() {
        
        Events.subscribe(EntityDamageByEntityEvent.class)
               .filter(e -> e.getEntity() instanceof Player)
               .filter(e -> e.getDamager() instanceof Snowball)
               .filter(e -> ((Snowball) e.getDamager()).getShooter() instanceof Player)
               .handler(e -> {
                   Player shooter = (Player) ((Snowball) e.getDamager()).getShooter();
                   e.getEntity().teleport(shooter.getLocation());
                   shooter.teleport(e.getEntity().getLocation());
                   e.getDamager().getWorld().playSound(e.getDamager().getLocation(), Sound.ENDERMAN_TELEPORT, 5.0F, 1.0F);
               });

//        ProjectileLaunchEvent
//        ProjectileHitEvent
    }
}
