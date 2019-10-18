package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInviteArenaEvent extends Event {

    private final Player host;
    private final Player invited;
    private static final HandlerList handlers = new HandlerList();

    public PlayerInviteArenaEvent(Player host, Player invited) {
        this.host = host;
        this.invited = invited;
    }

    public Player getHost() {
        return host;
    }

    public Player getInvited() {
        return invited;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
