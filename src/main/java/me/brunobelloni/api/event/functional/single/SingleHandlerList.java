package me.brunobelloni.api.event.functional.single;

import me.brunobelloni.api.event.SingleSubscription;
import me.brunobelloni.api.event.functional.FunctionalHandlerList;
import me.brunobelloni.api.utils.Delegates;
import org.bukkit.event.Event;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author lucko
 */
public interface SingleHandlerList<T extends Event> extends FunctionalHandlerList<T, SingleSubscription<T>> {

    @Override
    default SingleHandlerList<T> consumer(Consumer<? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        return biConsumer(Delegates.consumerToBiConsumerSecond(handler));
    }

    @Override
    SingleHandlerList<T> biConsumer(BiConsumer<SingleSubscription<T>, ? super T> handler);
}
