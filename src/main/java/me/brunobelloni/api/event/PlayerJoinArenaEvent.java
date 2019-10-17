package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
//import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerJoinArenaEvent extends Event {

    private final Player player;
//    private final PlayerInteractEvent event;
    private static final HandlerList handlers = new HandlerList();

    public PlayerJoinArenaEvent(Player player) {
//        this.event = event;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

//    public PlayerInteractEvent getEvent() {
//        return event;
//    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
