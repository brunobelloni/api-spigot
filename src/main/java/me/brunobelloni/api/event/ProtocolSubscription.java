package me.brunobelloni.api.event;

import com.comphenix.protocol.PacketType;

import java.util.Set;

/**
 * Represents a subscription to a set of packet events.
 */
public interface ProtocolSubscription extends Subscription {

    /**
     * Gets the packet types handled by this subscription.
     *
     * @return the types
     */

    Set<PacketType> getPackets();

}
