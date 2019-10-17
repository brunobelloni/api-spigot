package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAcceptInviteArenaEvent extends Event {

    private final Player whoInvited;
    private final Player whoAccepted;
    private static final HandlerList handlers = new HandlerList();

    public PlayerAcceptInviteArenaEvent(Player whoInvited, Player whoAccepted) {
        this.whoInvited = whoInvited;
        this.whoAccepted = whoAccepted;
    }

    public Player getWhoInvited() {
        return whoInvited;
    }

    public Player getWhoAccepted() {
        return whoAccepted;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
