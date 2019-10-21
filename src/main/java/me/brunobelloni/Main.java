package me.brunobelloni;

import me.brunobelloni.api.commands.command.CommandManager;
import me.brunobelloni.api.event.Events;
import me.brunobelloni.api.event.filter.EventFilters;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin {

    private final PluginManager pluginManager = this.getServer().getPluginManager();

    private final CommandManager commandManager = new CommandManager(this); //where plugin is a plugin instance

    @Override
    public void onEnable() {
        // Automatically finds all classes that implements the CommandListener.class and registers their commands
        commandManager.registerCommands();
        //registers a generated help topic to bukkit
        commandManager.registerHelp();
        //so the /help PluginName displays our plugin's registered commands
    }

    @Override
    public void onDisable() {
    }

    public void eventRegisterTest() {
        Events.subscribe(PlayerJoinEvent.class).handler(e -> e.setJoinMessage("dsasda"));

        Events.subscribe(PlayerJoinEvent.class)
               .expireAfter(2, TimeUnit.MINUTES) // expire after 2 mins
               .expireAfter(1) // or after the event has been called 1 time
               .filter(e -> !e.getPlayer().isOp())
               .handler(e -> e.getPlayer().sendMessage("Wew! You were first to join the server since it restarted!"));

        Events.subscribe(PlayerMoveEvent.class, EventPriority.MONITOR)
               .filter(EventFilters.ignoreCancelled())
               .filter(EventFilters.ignoreSameBlock())
               .handler(e -> {
                   // handle
               });

        Events.merge(PlayerEvent.class, PlayerQuitEvent.class, PlayerKickEvent.class)
               .filter(e -> !e.getPlayer().isOp())
               .handler(e -> {
                   Bukkit.broadcastMessage("Player " + e.getPlayer() + " has left the server!");
               });

        Events.merge(Player.class)
               .bindEvent(PlayerDeathEvent.class, PlayerDeathEvent::getEntity)
               .bindEvent(PlayerQuitEvent.class, PlayerEvent::getPlayer)
               .handler(e -> {
                   // poof!
                   e.getLocation().getWorld().createExplosion(e.getLocation(), 1.0f);
               });

        Events.subscribe(PlayerInteractEvent.class)
               .filter(EventFilters.ignoreCancelled())
               .filter(PlayerInteractEvent::hasItem)
               .filter(PlayerInteractEvent::hasBlock)
               .filter(e -> e.getAction() == Action.RIGHT_CLICK_BLOCK)
               .filter(e -> e.getItem().getType() == Material.BLAZE_ROD)
               .filter(EventFilters.playerHasPermission("testplugin.infotool"))
               .handler(e -> {
                   // play some spooky gadget effect
                   e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CAT_PURR, 1.0f, 1.0f);
                   e.getPlayer().playEffect(EntityEffect.FIREWORK_EXPLODE);
               });

    }
}
