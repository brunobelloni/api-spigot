package me.brunobelloni;

import me.brunobelloni.api.event.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ApiEventHandler implements Listener {

    private JavaPlugin plugin;

    public ApiEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDualBeginArenaEvent(DuelBeginArenaEvent e) {
        System.out.println(e.getEventName());
    }

    @EventHandler
    public void onDuelEndArenaEvent(DuelEndArenaEvent e) {
        System.out.println(e.getEventName());
    }

    @EventHandler
    public void onPlayerAcceptInviteArenaEvent(PlayerAcceptInviteArenaEvent e) {
        System.out.println(e.getEventName());
    }

    @EventHandler
    public void onPlayerDenyInviteArenaEvent(PlayerDenyInviteArenaEvent e) {
        System.out.println(e.getEventName());
    }

    @EventHandler
    public void onPlayerInviteArenaEvent(PlayerInviteArenaEvent e) {
        System.out.println(e.getEventName());
    }

    @EventHandler
    public void onPlayerJoinArenaEvent(PlayerJoinArenaEvent e) {
        System.out.println(e.getEventName());
    }

    @EventHandler
    public void onPlayerLeaveArenaEvent(PlayerLeaveArenaEvent e) {
        System.out.println(e.getEventName());
    }
}
