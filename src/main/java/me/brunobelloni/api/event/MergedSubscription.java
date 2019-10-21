package me.brunobelloni.api.event;

import org.bukkit.event.Event;

import java.util.Set;

/**
 * Represents a subscription to a set of events.
 *
 * @param <T> the handled type
 */
public interface MergedSubscription<T> extends Subscription {

    /**
     * Gets the handled class
     *
     * @return the handled class
     */
    Class<? super T> getHandledClass();

    /**
     * Gets a set of the individual event classes being listened to
     *
     * @return the individual classes
     */
    Set<Class<? extends Event>> getEventClasses();

}
