package me.brunobelloni.api.event.functional.single;

import me.brunobelloni.api.event.SingleSubscription;
import me.brunobelloni.api.event.functional.ExpiryTestStage;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

class SingleSubscriptionBuilderImpl<T extends Event> implements SingleSubscriptionBuilder<T> {
    final Class<T> eventClass;
    final EventPriority priority;
    final List<Predicate<T>> filters = new ArrayList<>(3);
    final List<BiPredicate<SingleSubscription<T>, T>> preExpiryTests = new ArrayList<>(0);
    final List<BiPredicate<SingleSubscription<T>, T>> midExpiryTests = new ArrayList<>(0);
    final List<BiPredicate<SingleSubscription<T>, T>> postExpiryTests = new ArrayList<>(0);
    BiConsumer<? super T, Throwable> exceptionConsumer = DEFAULT_EXCEPTION_CONSUMER;

    SingleSubscriptionBuilderImpl(Class<T> eventClass, EventPriority priority) {
        this.eventClass = eventClass;
        this.priority = priority;
    }


    @Override
    public SingleSubscriptionBuilder<T> expireIf(BiPredicate<SingleSubscription<T>, T> predicate, ExpiryTestStage... testPoints) {
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
    public SingleSubscriptionBuilder<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        this.filters.add(predicate);
        return this;
    }


    @Override
    public SingleSubscriptionBuilder<T> exceptionConsumer(BiConsumer<? super T, Throwable> exceptionConsumer) {
        Objects.requireNonNull(exceptionConsumer, "exceptionConsumer");
        this.exceptionConsumer = exceptionConsumer;
        return this;
    }


    @Override
    public SingleHandlerList<T> handlers() {
        return new SingleHandlerListImpl<>(this);
    }

}
