package java.util.stream;

import java.util.function.DoubleConsumer;

/**
 * Represents an operation that accepts a {@code double}-valued argument
 * and a DoubleConsumer, and returns no result. This functional interface is
 * to replace a double value with zero or more double values.
 *
 * <p>This is a <a href="../function/package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(double, DoubleConsumer)}.
 *
 * @since 16
 */
interface DoubleMapMultiConsumer {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code dc} consumer.
     *
     * @param value the double value coming from upstream
     * @param dc a {@code DoubleConsumer} accepting the mapped values
     */
    void accept(double value, DoubleConsumer dc);
}