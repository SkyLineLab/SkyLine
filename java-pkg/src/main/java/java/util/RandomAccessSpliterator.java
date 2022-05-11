package java.util;

import java.util.function.Consumer;

@SuppressWarnings("NewApi")
final class RandomAccessSpliterator<E> implements Spliterator<E> {

    private final List<E> list;
    private int index; // current index, modified on advance/split
    private int fence; // -1 until used; then one past last index

    // The following fields are valid if covering an AbstractList
    private final AbstractList<E> alist;
    private int expectedModCount; // initialized when fence set

    RandomAccessSpliterator(List<E> list) {
        assert list instanceof RandomAccess;

        this.list = list;
        this.index = 0;
        this.fence = -1;

        this.alist = list instanceof AbstractList ? (AbstractList<E>) list : null;
        this.expectedModCount = alist != null ? alist.modCount : 0;
    }

    /** Create new spliterator covering the given  range */
    private RandomAccessSpliterator(RandomAccessSpliterator<E> parent,
                                    int origin, int fence) {
        this.list = parent.list;
        this.index = origin;
        this.fence = fence;

        this.alist = parent.alist;
        this.expectedModCount = parent.expectedModCount;
    }

    private int getFence() { // initialize fence to size on first use
        int hi;
        List<E> lst = list;
        if ((hi = fence) < 0) {
            if (alist != null) {
                expectedModCount = alist.modCount;
            }
            hi = fence = lst.size();
        }
        return hi;
    }

    public Spliterator<E> trySplit() {
        int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
        return (lo >= mid) ? null : // divide range in half unless too small
                new RandomAccessSpliterator(this, lo, index = mid);
    }

    public boolean tryAdvance(Consumer<? super E> action) {
        if (action == null)
            throw new NullPointerException();
        int hi = getFence(), i = index;
        if (i < hi) {
            index = i + 1;
            action.accept(get(list, i));
            checkAbstractListModCount(alist, expectedModCount);
            return true;
        }
        return false;
    }

    public void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        List<E> lst = list;
        int hi = getFence();
        int i = index;
        index = hi;
        for (; i < hi; i++) {
            action.accept(get(lst, i));
        }
        checkAbstractListModCount(alist, expectedModCount);
    }

    public long estimateSize() {
        return (long) (getFence() - index);
    }

    public int characteristics() {
        return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
    }

    private static <E> E get(List<E> list, int i) {
        try {
            return list.get(i);
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    static void checkAbstractListModCount(AbstractList<?> alist, int expectedModCount) {
        if (alist != null && alist.modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
}
