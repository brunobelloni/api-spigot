package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDenyInviteArenaEvent extends Event {

    private final Player whoInvited;
    private final Player whoDenied;
    private static final HandlerList handlers = new HandlerList();

    public PlayerDenyInviteArenaEvent(Player whoInvited, Player whoDenied) {
        this.whoInvited = whoInvited;
        this.whoDenied = whoDenied;
    }

    public Player getWhoInvited() {
        return whoInvited;
    }

    public Player getWhoDenied() {
        return whoDenied;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
