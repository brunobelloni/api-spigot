package me.brunobelloni.api.event.terminable;


/**
 * An extension of {@link AutoCloseable}.
 * @author lucko
 */
@FunctionalInterface
public interface Terminable extends AutoCloseable {
    Terminable EMPTY = () -> {
    };

    /**
     * Closes this resource.
     */
    @Override
    void close() throws Exception;

    /**
     * Gets if the object represented by this instance is already permanently closed.
     *
     * @return true if this terminable is closed permanently
     */
    default boolean isClosed() {
        return false;
    }

    /**
     * Silently closes this resource, and returns the exception if one is thrown.
     *
     * @return the exception is one is thrown
     */
    default Exception closeSilently() {
        try {
            close();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    /**
     * Closes this resource, and prints the exception if one is thrown.
     */
    default void closeAndReportException() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Binds this terminable with a terminable consumer
     *
     * @param consumer the terminable consumer
     */
    default void bindWith(TerminableConsumer consumer) {
        consumer.bind(this);
    }

}
