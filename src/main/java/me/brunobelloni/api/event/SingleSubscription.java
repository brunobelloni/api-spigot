package me.brunobelloni.api.event;

import org.bukkit.event.Event;


/**
 * Represents a subscription to a single given event.
 *
 * @param <T> the event type
 */
public interface SingleSubscription<T extends Event> extends Subscription {

    /**
     * Gets the class the handler is handling
     *
     * @return the class the handler is handling.
     */
    Class<T> getEventClass();

}
