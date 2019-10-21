package me.brunobelloni.api.event.functional.single;

import me.brunobelloni.api.event.SingleSubscription;
import me.brunobelloni.api.event.internal.LoaderUtils;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author lucko
 */
class SingleHandlerListImpl<T extends Event> implements SingleHandlerList<T> {
    private final SingleSubscriptionBuilderImpl<T> builder;
    private final List<BiConsumer<SingleSubscription<T>, ? super T>> handlers = new ArrayList<>(1);

    SingleHandlerListImpl(SingleSubscriptionBuilderImpl<T> builder) {
        this.builder = builder;
    }

    @Override
    public SingleHandlerList<T> biConsumer(BiConsumer<SingleSubscription<T>, ? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        this.handlers.add(handler);
        return this;
    }

    @Override
    public SingleSubscription<T> register() {
        if (this.handlers.isEmpty()) {
            throw new IllegalStateException("No handlers have been registered");
        }

        HelperEventListener<T> listener = new HelperEventListener<>(this.builder, this.handlers);
        listener.register(LoaderUtils.getPlugin());
        return listener;
    }
}
