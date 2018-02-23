package de._7p.solcon.fit.logging.common.formatters.tostring.common;

public final class Pair<A,B> extends AbstractPair<A,B>{

    public Pair(A first, B second) {
        super(first, second);
    }

    /**
     * get first object
     * @return first object
     */
    public A getLeft() {
        return super.getFirst();
    }

    /**
     * get second object
     * @return second object
     */
    public B getRight() {
        return super.getSecond();
    }

}
