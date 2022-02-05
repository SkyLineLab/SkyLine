/*
 * Written by Stefan Zobel and released to the
 * public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */
package java.util;

import java.util.Comparator;

import java.util.function.Consumer;

/**
 * A j8.u.Spliterator implementation that delegates to a j.u.Spliterator.
 *
 * @param <T>
 *            the type of the input to the operation
 */
final class DelegatingSpliterator<T> implements Spliterator<T> {

    private final Spliterator<T> spliter;

    DelegatingSpliterator(Spliterator<T> spliterator) {
        Objects.requireNonNull(spliterator);
        this.spliter = spliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return spliter.tryAdvance(new ConsumerDelegate<>(action));
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> spliterator = spliter.trySplit();
        if (spliterator == null) {
            return null;
        }
        return new DelegatingSpliterator<>(spliterator);
    }

    @Override
    public long estimateSize() {
        return spliter.estimateSize();
    }

    @Override
    public int characteristics() {
        return spliter.characteristics();
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        spliter.forEachRemaining(new ConsumerDelegate<>(action));
    }

    @Override
    public long getExactSizeIfKnown() {
        return spliter.getExactSizeIfKnown();
    }

    @Override
    public boolean hasCharacteristics(int characteristics) {
        return spliter.hasCharacteristics(characteristics);
    }

    @Override
    public Comparator<? super T> getComparator() {
        return spliter.getComparator();
    }

    /**
     * A j.u.f.Consumer implementation that delegates to a j8.u.f.Consumer.
     *
     * @param <T>
     *            the type of the input to the operation
     */
    private static final class ConsumerDelegate<T> implements
            Consumer<T> {

        private final Consumer<T> delegate;

        ConsumerDelegate(Consumer<T> delegate) {
            Objects.requireNonNull(delegate);
            this.delegate = delegate;
        }

        @Override
        public void accept(T t) {
            delegate.accept(t);
        }

        @Override
        public Consumer<T> andThen(
                Consumer<? super T> after) {

            Objects.requireNonNull(after);

            return new ConsumerDelegate<T>(delegate.andThen(
                    new Consumer<T>() {
                        @Override
                        public void accept(T t) {
                            after.accept(t);
                        }
                    }));
        }
    }
}
