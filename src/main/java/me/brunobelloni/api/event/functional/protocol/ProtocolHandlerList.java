package me.brunobelloni.api.event.functional.protocol;

import com.comphenix.protocol.events.PacketEvent;
import me.brunobelloni.api.event.ProtocolSubscription;
import me.brunobelloni.api.event.functional.FunctionalHandlerList;
import me.brunobelloni.api.event.utils.Delegates;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ProtocolHandlerList extends FunctionalHandlerList<PacketEvent, ProtocolSubscription> {


    @Override
    default ProtocolHandlerList consumer(Consumer<? super PacketEvent> handler) {
        Objects.requireNonNull(handler, "handler");
        return biConsumer(Delegates.consumerToBiConsumerSecond(handler));
    }


    @Override
    ProtocolHandlerList biConsumer(BiConsumer<ProtocolSubscription, ? super PacketEvent> handler);

}
