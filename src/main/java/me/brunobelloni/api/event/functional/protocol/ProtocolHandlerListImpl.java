package me.brunobelloni.api.event.functional.protocol;

import com.comphenix.protocol.events.PacketEvent;
import me.brunobelloni.api.event.ProtocolSubscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

class ProtocolHandlerListImpl implements ProtocolHandlerList {
    private final ProtocolSubscriptionBuilderImpl builder;
    private final List<BiConsumer<ProtocolSubscription, ? super PacketEvent>> handlers = new ArrayList<>(1);

    ProtocolHandlerListImpl(ProtocolSubscriptionBuilderImpl builder) {
        this.builder = builder;
    }

    @Override
    public ProtocolHandlerList biConsumer(BiConsumer<ProtocolSubscription, ? super PacketEvent> handler) {
        Objects.requireNonNull(handler, "handler");
        this.handlers.add(handler);
        return this;
    }

    @Override
    public ProtocolSubscription register() {
        return new HelperProtocolListener(builder, handlers);
    }
}
