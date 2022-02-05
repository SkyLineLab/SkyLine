package java.util;

/**
 * Compares {@link Comparable} objects in natural order.
 *
 * @see Comparable
 */
enum NaturalOrderComparator implements Comparator<Comparable<Object>> {
    INSTANCE;

    @Override
    public int compare(Comparable<Object> c1, Comparable<Object> c2) {
        return c1.compareTo(c2);
    }

    public Comparator<Comparable<Object>> reversed() {
        return Comparators.reverseOrder();
    }
}