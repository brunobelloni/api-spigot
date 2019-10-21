package me.brunobelloni.api.event.terminable.module;

import me.brunobelloni.api.event.terminable.Terminable;
import me.brunobelloni.api.event.terminable.TerminableConsumer;

/**
 * A terminable module is a class which manipulates and constructs a number
 * of {@link Terminable}s.
 */
public interface TerminableModule {

    /**
     * Performs the tasks to setup this module
     *
     * @param consumer the terminable consumer
     */
    void setup(TerminableConsumer consumer);

    /**
     * Registers this terminable with a terminable consumer
     *
     * @param consumer the terminable consumer
     */
    default void bindModuleWith(TerminableConsumer consumer) {
        consumer.bindModule(this);
    }

}
