package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Evento que é chamado quando o player deixa a arena
 * Pode ser saida voluntaria, ou desconexão com o servidor
 */
public class PlayerLeaveArenaEvent extends Event {

    private final Player player;
    private static final HandlerList handlers = new HandlerList();

    public PlayerLeaveArenaEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
