package me.brunobelloni.api.event;

import com.google.common.reflect.TypeToken;
import me.brunobelloni.api.event.functional.merged.MergedSubscriptionBuilder;
import me.brunobelloni.api.event.functional.single.SingleSubscriptionBuilder;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

/**
 * A functional event listening utility.
 * @author lucko
 */
public final class Events {

    private Events() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Makes a SingleSubscriptionBuilder for a given event
     *
     * @param eventClass the class of the event
     * @param <T>        the event type
     * @return a {@link SingleSubscriptionBuilder} to construct the event handler
     * @throws NullPointerException if eventClass is null
     */
    public static <T extends Event> SingleSubscriptionBuilder<T> subscribe(Class<T> eventClass) {
        return SingleSubscriptionBuilder.newBuilder(eventClass);
    }

    /**
     * Makes a SingleSubscriptionBuilder for a given event
     *
     * @param eventClass the class of the event
     * @param priority   the priority to listen at
     * @param <T>        the event type
     * @return a {@link SingleSubscriptionBuilder} to construct the event handler
     * @throws NullPointerException if eventClass or priority is null
     */
    public static <T extends Event> SingleSubscriptionBuilder<T> subscribe(Class<T> eventClass, EventPriority priority) {
        return SingleSubscriptionBuilder.newBuilder(eventClass, priority);
    }

    /**
     * Makes a MergedSubscriptionBuilder for a given super type
     *
     * @param handledClass the super type of the event handler
     * @param <T>          the super type class
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */
    public static <T> MergedSubscriptionBuilder<T> merge(Class<T> handledClass) {
        return MergedSubscriptionBuilder.newBuilder(handledClass);
    }

    /**
     * Makes a MergedSubscriptionBuilder for a given super type
     *
     * @param type the super type of the event handler
     * @param <T>  the super type class
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */
    public static <T> MergedSubscriptionBuilder<T> merge(TypeToken<T> type) {
        return MergedSubscriptionBuilder.newBuilder(type);
    }

    /**
     * Makes a MergedSubscriptionBuilder for a super event class
     *
     * @param superClass   the abstract super event class
     * @param eventClasses the event classes to be bound to
     * @param <S>          the super class type
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */
    @SafeVarargs
    public static <S extends Event> MergedSubscriptionBuilder<S> merge(Class<S> superClass, Class<? extends S>... eventClasses) {
        return MergedSubscriptionBuilder.newBuilder(superClass, eventClasses);
    }

    /**
     * Submit the event on the current thread
     *
     * @param event the event to call
     */
//    public static void call(Event event) {
//        Helper.plugins().callEvent(event);
//    }

    /**
     * Submit the event on a new async thread.
     *
     * @param event the event to call
     */
//    public static void callAsync(Event event) {
//        Schedulers.async().run(() -> call(event));
//    }

    /**
     * Submit the event on the main server thread.
     *
     * @param event the event to call
     */
//    public static void callSync(Event event) {
//        Schedulers.sync().run(() -> call(event));
//    }

    /**
     * Submit the event on the current thread
     *
     * @param event the event to call
     */
//    public static <T extends Event> T callAndReturn(T event) {
//        Helper.plugins().callEvent(event);
//        return event;
//    }

    /**
     * Submit the event on a new async thread.
     *
     * @param event the event to call
     */
//    public static <T extends Event> T callAsyncAndJoin(T event) {
//        return Schedulers.async().supply(() -> callAndReturn(event)).join();
//    }

    /**
     * Makes a MergedSubscriptionBuilder for a super event class
     *
     * @param superClass   the abstract super event class
     * @param priority     the priority to listen at
     * @param eventClasses the event classes to be bound to
     * @param <S>          the super class type
     * @return a {@link MergedSubscriptionBuilder} to construct the event handler
     */
    @SafeVarargs
    public static <S extends Event> MergedSubscriptionBuilder<S> merge(Class<S> superClass, EventPriority priority, Class<? extends S>... eventClasses) {
        return MergedSubscriptionBuilder.newBuilder(superClass, priority, eventClasses);
    }

}
