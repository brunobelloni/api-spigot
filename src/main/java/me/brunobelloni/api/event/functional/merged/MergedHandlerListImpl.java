package me.brunobelloni.api.event.functional.merged;

import me.brunobelloni.api.event.MergedSubscription;
import me.brunobelloni.api.event.internal.LoaderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author lucko
 */
class MergedHandlerListImpl<T> implements MergedHandlerList<T> {

    private final MergedSubscriptionBuilderImpl<T> builder;
    private final List<BiConsumer<MergedSubscription<T>, ? super T>> handlers = new ArrayList<>(1);

    MergedHandlerListImpl(MergedSubscriptionBuilderImpl<T> builder) {
        this.builder = builder;
    }

    @Override
    public MergedHandlerList<T> biConsumer(BiConsumer<MergedSubscription<T>, ? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        this.handlers.add(handler);
        return this;
    }

    @Override
    public MergedSubscription<T> register() {
        if (this.handlers.isEmpty()) {
            throw new IllegalStateException("No handlers have been registered");
        }

        HelperMergedEventListener<T> listener = new HelperMergedEventListener<>(this.builder, this.handlers);
        listener.register(LoaderUtils.getPlugin());
        return listener;
    }
}
