package de._7p.solcon.fit.common;

import org.junit.Assert;
import org.junit.Test;

import de._7p.solcon.fit.logging.common.asserts.UncheckedThrow;

public final class UncheckedThrowTest {

    @Test
    public void test() {
        try {
            f0();
        }
        catch (final CheckedException e) {
            Assert.assertTrue(e.getMessage().equals("misst!"));
        }
    }

    private static int f0() throws CheckedException{
        return f1();
    }

    private static int f1(){
        return f2();
    }

    private static int f2() {

        try {
            return f3();
        }
        catch (final CheckedException e) {
            throw UncheckedThrow.throwUnchecked(e);
        }
    }

    private static int f3() throws CheckedException{

        throw new CheckedException("misst!");
    }

    @SuppressWarnings("serial")
    private static class CheckedException extends Exception{
        public CheckedException(final String msg) {
            super(msg);
        }
    }
}
