package me.brunobelloni.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelEndArenaEvent extends Event {

    private final Player winner;
    private final Player loser;
    private static final HandlerList handlers = new HandlerList();

    public DuelEndArenaEvent(Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
