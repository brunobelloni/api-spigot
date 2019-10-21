package me.brunobelloni.api.event.functional.merged;

import me.brunobelloni.api.event.MergedSubscription;
import me.brunobelloni.api.event.functional.FunctionalHandlerList;
import me.brunobelloni.api.utils.Delegates;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface MergedHandlerList<T> extends FunctionalHandlerList<T, MergedSubscription<T>> {


    @Override
    default MergedHandlerList<T> consumer(Consumer<? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        return biConsumer(Delegates.consumerToBiConsumerSecond(handler));
    }


    @Override
    MergedHandlerList<T> biConsumer(BiConsumer<MergedSubscription<T>, ? super T> handler);
}
