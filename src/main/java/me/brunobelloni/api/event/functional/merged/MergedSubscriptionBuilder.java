package me.brunobelloni.api.event.functional.merged;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import me.brunobelloni.api.event.MergedSubscription;
import me.brunobelloni.api.event.functional.ExpiryTestStage;
import me.brunobelloni.api.event.functional.SubscriptionBuilder;
import me.brunobelloni.api.utils.Delegates;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * Functional builder for {@link MergedSubscription}s.
 *
 * @author lucko
 * @param <T> the handled type
 */
public interface MergedSubscriptionBuilder<T> extends SubscriptionBuilder<T> {

    /**
     * Makes a MergedHandlerBuilder for a given super type
     *
     * @param handledClass the super type of the event handler
     * @param <T>          the super type class
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */

    static <T> MergedSubscriptionBuilder<T> newBuilder(Class<T> handledClass) {
        Objects.requireNonNull(handledClass, "handledClass");
        return new MergedSubscriptionBuilderImpl<>(TypeToken.of(handledClass));
    }

    /**
     * Makes a MergedHandlerBuilder for a given super type
     *
     * @param type the super type of the event handler
     * @param <T>  the super type class
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */

    static <T> MergedSubscriptionBuilder<T> newBuilder(TypeToken<T> type) {
        Objects.requireNonNull(type, "type");
        return new MergedSubscriptionBuilderImpl<>(type);
    }

    /**
     * Makes a MergedHandlerBuilder for a super event class
     *
     * @param superClass   the abstract super event class
     * @param eventClasses the event classes to be bound to
     * @param <S>          the super class type
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */

    @SafeVarargs
    static <S extends Event> MergedSubscriptionBuilder<S> newBuilder(Class<S> superClass, Class<? extends S>... eventClasses) {
        return newBuilder(superClass, EventPriority.NORMAL, eventClasses);
    }

    /**
     * Makes a MergedHandlerBuilder for a super event class
     *
     * @param superClass   the abstract super event class
     * @param priority     the priority to listen at
     * @param eventClasses the event classes to be bound to
     * @param <S>          the super class type
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */

    @SafeVarargs
    static <S extends Event> MergedSubscriptionBuilder<S> newBuilder(Class<S> superClass, EventPriority priority, Class<? extends S>... eventClasses) {
        Objects.requireNonNull(superClass, "superClass");
        Objects.requireNonNull(eventClasses, "eventClasses");
        Objects.requireNonNull(priority, "priority");
        if (eventClasses.length < 2) {
            throw new IllegalArgumentException("merge method used for only one subclass");
        }

        MergedSubscriptionBuilderImpl<S> h = new MergedSubscriptionBuilderImpl<>(TypeToken.of(superClass));
        for (Class<? extends S> clazz : eventClasses) {
            h.bindEvent(clazz, priority, e -> e);
        }
        return h;
    }

    @Override
    default MergedSubscriptionBuilder<T> expireIf(Predicate<T> predicate) {
        return expireIf(Delegates.predicateToBiPredicateSecond(predicate), ExpiryTestStage.PRE, ExpiryTestStage.POST_HANDLE);
    }

    @Override
    default MergedSubscriptionBuilder<T> expireAfter(long duration, TimeUnit unit) {
        Objects.requireNonNull(unit, "unit");
        Preconditions.checkArgument(duration >= 1, "duration < 1");
        long expiry = Math.addExact(System.currentTimeMillis(), unit.toMillis(duration));
        return expireIf((handler, event) -> System.currentTimeMillis() > expiry, ExpiryTestStage.PRE);
    }

    @Override
    default MergedSubscriptionBuilder<T> expireAfter(long maxCalls) {
        Preconditions.checkArgument(maxCalls >= 1, "maxCalls < 1");
        return expireIf((handler, event) -> handler.getCallCounter() >= maxCalls, ExpiryTestStage.PRE, ExpiryTestStage.POST_HANDLE);
    }

    @Override
    MergedSubscriptionBuilder<T> filter(Predicate<T> predicate);

    /**
     * Add a expiry predicate.
     *
     * @param predicate  the expiry test
     * @param testPoints when to test the expiry predicate
     * @return ths builder instance
     */
    MergedSubscriptionBuilder<T> expireIf(BiPredicate<MergedSubscription<T>, T> predicate, ExpiryTestStage... testPoints);

    /**
     * Binds this handler to an event
     *
     * @param eventClass the event class to bind to
     * @param function   the function to remap the event
     * @param <E>        the event class
     * @return the builder instance
     */
    <E extends Event> MergedSubscriptionBuilder<T> bindEvent(Class<E> eventClass, Function<E, T> function);

    /**
     * Binds this handler to an event
     *
     * @param eventClass the event class to bind to
     * @param priority   the priority to listen at
     * @param function   the function to remap the event
     * @param <E>        the event class
     * @return the builder instance
     */
    <E extends Event> MergedSubscriptionBuilder<T> bindEvent(Class<E> eventClass, EventPriority priority, Function<E, T> function);

    /**
     * Sets the exception consumer for the handler.
     *
     * <p> If an exception is thrown in the handler, it is passed to this consumer to be swallowed.
     *
     * @param consumer the consumer
     * @return the builder instance
     * @throws NullPointerException if the consumer is null
     */
    MergedSubscriptionBuilder<T> exceptionConsumer(BiConsumer<Event, Throwable> consumer);

    /**
     * Return the handler list builder to append handlers for the event.
     *
     * @return the handler list
     */
    MergedHandlerList<T> handlers();

    /**
     * Builds and registers the Handler.
     *
     * @param handler the consumer responsible for handling the event.
     * @return a registered {@link MergedSubscription} instance.
     * @throws NullPointerException  if the handler is null
     * @throws IllegalStateException if no events have been bound to
     */
    default MergedSubscription<T> handler(Consumer<? super T> handler) {
        return handlers().consumer(handler).register();
    }

    /**
     * Builds and registers the Handler.
     *
     * @param handler the bi-consumer responsible for handling the event.
     * @return a registered {@link MergedSubscription} instance.
     * @throws NullPointerException  if the handler is null
     * @throws IllegalStateException if no events have been bound to
     */
    default MergedSubscription<T> biHandler(BiConsumer<MergedSubscription<T>, ? super T> handler) {
        return handlers().biConsumer(handler).register();
    }

}
