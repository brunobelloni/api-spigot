package me.brunobelloni.api.event.functional.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.brunobelloni.api.event.ProtocolSubscription;
import me.brunobelloni.api.event.internal.LoaderUtils;
import me.brunobelloni.api.event.protocol.Protocol;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

class HelperProtocolListener extends PacketAdapter implements ProtocolSubscription {
    private final Set<PacketType> types;

    private final BiConsumer<? super PacketEvent, Throwable> exceptionConsumer;

    private final Predicate<PacketEvent>[] filters;
    private final BiPredicate<ProtocolSubscription, PacketEvent>[] preExpiryTests;
    private final BiPredicate<ProtocolSubscription, PacketEvent>[] midExpiryTests;
    private final BiPredicate<ProtocolSubscription, PacketEvent>[] postExpiryTests;
    private final BiConsumer<ProtocolSubscription, ? super PacketEvent>[] handlers;

    private final AtomicLong callCount = new AtomicLong(0);
    private final AtomicBoolean active = new AtomicBoolean(true);

    @SuppressWarnings("unchecked")
    HelperProtocolListener(ProtocolSubscriptionBuilderImpl builder, List<BiConsumer<ProtocolSubscription, ? super PacketEvent>> handlers) {
        super(LoaderUtils.getPlugin(), builder.priority, builder.types);

        this.types = builder.types;
        this.exceptionConsumer = builder.exceptionConsumer;

        this.filters = builder.filters.toArray(new Predicate[builder.filters.size()]);
        this.preExpiryTests = builder.preExpiryTests.toArray(new BiPredicate[builder.preExpiryTests.size()]);
        this.midExpiryTests = builder.midExpiryTests.toArray(new BiPredicate[builder.midExpiryTests.size()]);
        this.postExpiryTests = builder.postExpiryTests.toArray(new BiPredicate[builder.postExpiryTests.size()]);
        this.handlers = handlers.toArray(new BiConsumer[handlers.size()]);

        Protocol.manager().addPacketListener(this);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        onPacket(event);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        onPacket(event);
    }

    private void onPacket(PacketEvent event) {
        // check we actually want this event
        if (!this.types.contains(event.getPacketType())) {
            return;
        }

        // this handler is disabled, so don't listen
        if (!this.active.get()) {
            return;
        }

        // check pre-expiry tests
        for (BiPredicate<ProtocolSubscription, PacketEvent> test : this.preExpiryTests) {
            if (test.test(this, event)) {
                unregister();
                return;
            }
        }

        // begin "handling" of the event
        try {
            // check the filters
            for (Predicate<PacketEvent> filter : this.filters) {
                if (!filter.test(event)) {
                    return;
                }
            }

            // check mid-expiry tests
            for (BiPredicate<ProtocolSubscription, PacketEvent> test : this.midExpiryTests) {
                if (test.test(this, event)) {
                    unregister();
                    return;
                }
            }

            // call the handler
            for (BiConsumer<ProtocolSubscription, ? super PacketEvent> handler : this.handlers) {
                handler.accept(this, event);
            }

            // increment call counter
            this.callCount.incrementAndGet();
        } catch (Throwable t) {
            this.exceptionConsumer.accept(event, t);
        }

        // check post-expiry tests
        for (BiPredicate<ProtocolSubscription, PacketEvent> test : this.postExpiryTests) {
            if (test.test(this, event)) {
                unregister();
                return;
            }
        }
    }


    @Override
    public Set<PacketType> getPackets() {
        return this.types;
    }

    @Override
    public boolean isActive() {
        return this.active.get();
    }

    @Override
    public boolean isClosed() {
        return !this.active.get();
    }

    @Override
    public long getCallCounter() {
        return this.callCount.get();
    }

    @Override
    public boolean unregister() {
        // already unregistered
        if (!this.active.getAndSet(false)) {
            return false;
        }

        Protocol.manager().removePacketListener(this);
        return true;
    }
}
