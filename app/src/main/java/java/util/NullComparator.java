package java.util;

import java.io.Serializable;

/**
 * Null-friendly comparators
 */
final class NullComparator<T> implements Comparator<T>, Serializable {
    private static final long serialVersionUID = -7569533591570686392L;
    private final boolean nullFirst;
    // if null, non-null Ts are considered equal
    private final Comparator<T> real;

    @SuppressWarnings("unchecked")
    NullComparator(boolean nullFirst, Comparator<? super T> real) {
        this.nullFirst = nullFirst;
        this.real = (Comparator<T>) real;
    }

    @Override
    public int compare(T a, T b) {
        if (a == null) {
            return (b == null) ? 0 : (nullFirst ? -1 : 1);
        } else if (b == null) {
            return nullFirst ? 1: -1;
        } else {
            return (real == null) ? 0 : real.compare(a, b);
        }
    }

    public Comparator<T> thenComparing(Comparator<? super T> other) {
        Objects.requireNonNull(other);
        return new NullComparator<T>(nullFirst, real == null ? other : Comparators.thenComparing(real, other));
    }

    public Comparator<T> reversed() {
        return new NullComparator<T>(!nullFirst, real == null ? null : Collections.reverseOrder(real));
    }
}