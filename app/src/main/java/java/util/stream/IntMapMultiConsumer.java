package java.util.stream;

import java.util.function.IntConsumer;

/**
 * Represents an operation that accepts an {@code int}-valued argument
 * and an IntConsumer, and returns no result. This functional interface is
 * to replace an int value with zero or more int values.
 *
 * <p>This is a <a href="../function/package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(int, IntConsumer)}.
 *
 *
 * @since 16
 */
interface IntMapMultiConsumer {

    /**
     * Replaces the given {@code value} with zero or more values by feeding the mapped
     * values to the {@code ic} consumer.
     *
     * @param value the int value coming from upstream
     * @param ic an {@code IntConsumer} accepting the mapped values
     */
    void accept(int value, IntConsumer ic);
}