package me.brunobelloni.api.event.functional.merged;

import com.google.common.reflect.TypeToken;
import me.brunobelloni.api.event.MergedSubscription;
import me.brunobelloni.api.event.functional.ExpiryTestStage;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author lucko
 */
class MergedSubscriptionBuilderImpl<T> implements MergedSubscriptionBuilder<T> {

    final TypeToken<T> handledClass;
    final Map<Class<? extends Event>, MergedHandlerMapping<T, ? extends Event>> mappings = new HashMap<>();
    final List<Predicate<T>> filters = new ArrayList<>();
    final List<BiPredicate<MergedSubscription<T>, T>> preExpiryTests = new ArrayList<>(0);
    final List<BiPredicate<MergedSubscription<T>, T>> midExpiryTests = new ArrayList<>(0);
    final List<BiPredicate<MergedSubscription<T>, T>> postExpiryTests = new ArrayList<>(0);
    BiConsumer<? super Event, Throwable> exceptionConsumer = DEFAULT_EXCEPTION_CONSUMER;

    MergedSubscriptionBuilderImpl(TypeToken<T> handledClass) {
        this.handledClass = handledClass;
    }

    @Override
    public <E extends Event> MergedSubscriptionBuilder<T> bindEvent(Class<E> eventClass, Function<E, T> function) {
        return bindEvent(eventClass, EventPriority.NORMAL, function);
    }

    @Override
    public <E extends Event> MergedSubscriptionBuilder<T> bindEvent(Class<E> eventClass, EventPriority priority, Function<E, T> function) {
        Objects.requireNonNull(eventClass, "eventClass");
        Objects.requireNonNull(priority, "priority");
        Objects.requireNonNull(function, "function");

        this.mappings.put(eventClass, new MergedHandlerMapping<>(priority, function));
        return this;
    }

    @Override
    public MergedSubscriptionBuilder<T> expireIf(BiPredicate<MergedSubscription<T>, T> predicate, ExpiryTestStage... testPoints) {
        Objects.requireNonNull(testPoints, "testPoints");
        Objects.requireNonNull(predicate, "predicate");
        for (ExpiryTestStage testPoint : testPoints) {
            switch (testPoint) {
                case PRE:
                    this.preExpiryTests.add(predicate);
                    break;
                case POST_FILTER:
                    this.midExpiryTests.add(predicate);
                    break;
                case POST_HANDLE:
                    this.postExpiryTests.add(predicate);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown ExpiryTestPoint: " + testPoint);
            }
        }
        return this;
    }

    @Override
    public MergedSubscriptionBuilder<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        this.filters.add(predicate);
        return this;
    }

    @Override
    public MergedSubscriptionBuilder<T> exceptionConsumer(BiConsumer<Event, Throwable> exceptionConsumer) {
        Objects.requireNonNull(exceptionConsumer, "exceptionConsumer");
        this.exceptionConsumer = exceptionConsumer;
        return this;
    }

    @Override
    public MergedHandlerList<T> handlers() {
        if (this.mappings.isEmpty()) {
            throw new IllegalStateException("No mappings were created");
        }

        return new MergedHandlerListImpl<>(this);
    }
}
