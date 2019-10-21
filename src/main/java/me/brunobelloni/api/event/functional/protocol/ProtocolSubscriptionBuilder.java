package me.brunobelloni.api.event.functional.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import me.brunobelloni.api.event.ProtocolSubscription;
import me.brunobelloni.api.event.functional.ExpiryTestStage;
import me.brunobelloni.api.event.functional.SubscriptionBuilder;
import me.brunobelloni.api.event.utils.Delegates;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Functional builder for {@link ProtocolSubscription}s.
 */
public interface ProtocolSubscriptionBuilder extends SubscriptionBuilder<PacketEvent> {

    /**
     * Makes a HandlerBuilder for the given packets
     *
     * @param packets the packets to handle
     * @return a {@link ProtocolSubscriptionBuilder} to construct the event handler
     */

    static ProtocolSubscriptionBuilder newBuilder(PacketType... packets) {
        return newBuilder(ListenerPriority.NORMAL, packets);
    }

    /**
     * Makes a HandlerBuilder for the given packets
     *
     * @param priority the priority to listen at
     * @param packets  the packets to handle
     * @return a {@link ProtocolSubscriptionBuilder} to construct the event handler
     */

    static ProtocolSubscriptionBuilder newBuilder(ListenerPriority priority, PacketType... packets) {
        Objects.requireNonNull(priority, "priority");
        Objects.requireNonNull(packets, "packets");
        return new ProtocolSubscriptionBuilderImpl(ImmutableSet.copyOf(packets), priority);
    }

    // override return type - we return SingleSubscriptionBuilder, not SubscriptionBuilder


    @Override
    default ProtocolSubscriptionBuilder expireIf(Predicate<PacketEvent> predicate) {
        return expireIf(Delegates.predicateToBiPredicateSecond(predicate), ExpiryTestStage.PRE, ExpiryTestStage.POST_HANDLE);
    }


    @Override
    default ProtocolSubscriptionBuilder expireAfter(long duration, TimeUnit unit) {
        Objects.requireNonNull(unit, "unit");
        Preconditions.checkArgument(duration >= 1, "duration < 1");
        long expiry = Math.addExact(System.currentTimeMillis(), unit.toMillis(duration));
        return expireIf((handler, event) -> System.currentTimeMillis() > expiry, ExpiryTestStage.PRE);
    }


    @Override
    default ProtocolSubscriptionBuilder expireAfter(long maxCalls) {
        Preconditions.checkArgument(maxCalls >= 1, "maxCalls < 1");
        return expireIf((handler, event) -> handler.getCallCounter() >= maxCalls, ExpiryTestStage.PRE, ExpiryTestStage.POST_HANDLE);
    }


    @Override
    ProtocolSubscriptionBuilder filter(Predicate<PacketEvent> predicate);

    /**
     * Add a expiry predicate.
     *
     * @param predicate  the expiry test
     * @param testPoints when to test the expiry predicate
     * @return ths builder instance
     */

    ProtocolSubscriptionBuilder expireIf(BiPredicate<ProtocolSubscription, PacketEvent> predicate, ExpiryTestStage... testPoints);

    /**
     * Sets the exception consumer for the handler.
     *
     * <p> If an exception is thrown in the handler, it is passed to this consumer to be swallowed.
     *
     * @param consumer the consumer
     * @return the builder instance
     * @throws NullPointerException if the consumer is null
     */

    ProtocolSubscriptionBuilder exceptionConsumer(BiConsumer<? super PacketEvent, Throwable> consumer);

    /**
     * Return the handler list builder to append handlers for the event.
     *
     * @return the handler list
     */

    ProtocolHandlerList handlers();

    /**
     * Builds and registers the Handler.
     *
     * @param handler the consumer responsible for handling the event.
     * @return a registered {@link ProtocolSubscription} instance.
     * @throws NullPointerException if the handler is null
     */

    default ProtocolSubscription handler(Consumer<? super PacketEvent> handler) {
        return handlers().consumer(handler).register();
    }

    /**
     * Builds and registers the Handler.
     *
     * @param handler the bi-consumer responsible for handling the event.
     * @return a registered {@link ProtocolSubscription} instance.
     * @throws NullPointerException if the handler is null
     */

    default ProtocolSubscription biHandler(BiConsumer<ProtocolSubscription, ? super PacketEvent> handler) {
        return handlers().biConsumer(handler).register();
    }

}
