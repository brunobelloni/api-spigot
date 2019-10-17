package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
//import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Evento que é chamado quando o player deixa a arena
 * Pode ser saida voluntaria, ou desconexão com o servidor
 */
public class PlayerLeaveArenaEvent extends Event {

    private final Player player;
    //    private final PlayerInteractEvent event;
    private static final HandlerList handlers = new HandlerList();

    public PlayerLeaveArenaEvent(Player player) {
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
