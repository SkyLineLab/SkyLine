package java.util.stream;

import java.util.function.LongConsumer;

/**
 * Represents an operation that accepts a {@code long}-valued argument
 * and a LongConsumer, and returns no result. This functional interface is
 * to replace a long value with zero or more long values.
 *
 * <p>This is a <a href="../function/package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(long, LongConsumer)}.
 *
 *
 * @since 16
 */
interface LongMapMultiConsumer {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code lc} consumer.
     *
     * @param value the long value coming from upstream
     * @param lc a {@code LongConsumer} accepting the mapped values
     */
    void accept(long value, LongConsumer lc);
}