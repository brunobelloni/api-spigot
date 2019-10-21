package me.brunobelloni.api.event.terminable;

import me.brunobelloni.api.event.terminable.module.TerminableModule;

/**
 * Accepts {@link AutoCloseable}s (and by inheritance {@link Terminable}s),
 * as well as {@link TerminableModule}s.
 */
@FunctionalInterface
public interface TerminableConsumer {

    /**
     * Binds with the given terminable.
     *
     * @param terminable the terminable to bind with
     * @param <T>        the terminable type
     * @return the same terminable
     */
    <T extends AutoCloseable> T bind(T terminable);

    /**
     * Binds with the given terminable module.
     *
     * @param module the module to bind with
     * @param <T>    the module type
     * @return the same module
     */
    default <T extends TerminableModule> T bindModule(T module) {
        module.setup(this);
        return module;
    }

}
