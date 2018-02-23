package de._7p.solcon.fit.common.common.common.formatters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de._7p.solcon.fit.logging.api.annotations.Logged.LogLevel;
import de._7p.solcon.fit.logging.common.formatters.MethodCallFormatter;

public class MethodCallFormatterTest {
    
    @Test
    public void testLogException_1()
        throws Exception {
        final String targetId = null;
        final Throwable e = new Throwable();
        final Logger logger = LoggerFactory.getLogger("");
        final String methodName = "methodName";
        final String clazzName = "ClazzName";

        MethodCallFormatter.logException(targetId, e, LogLevel.DEBUG, logger, methodName, clazzName);

        //fail("unverified");
    }

    @Test
    public void testLogException_2()
        throws Exception {
        final String targetId = "";
        final Throwable e = new Throwable();
        final Logger logger = LoggerFactory.getLogger("");
        final String methodName = "methodName";
        final String clazzName = "ClazzName";

        MethodCallFormatter.logException(targetId, e, LogLevel.DEBUG, logger, methodName, clazzName);

        //fail("unverified");
    }

    @Test
    public void testLogInvoke_1()
        throws Exception {
        final String targetId = null;
        final Object[] args = new Object[] {};
        final Logger logger = LoggerFactory.getLogger("");
        final String methodName = "methodName";
        final String clazzName = "ClazzName";

        MethodCallFormatter.logInvoke(targetId, args, LogLevel.DEBUG, logger, methodName, clazzName);

        //fail("unverified");
    }

    @Test
    public void testLogInvoke_2()
        throws Exception {
        final String targetId = "";
        final Object[] args = new Object[] {};
        final Logger logger = LoggerFactory.getLogger("");
        final String methodName = "methodName";
        final String clazzName = "ClazzName";

        MethodCallFormatter.logInvoke(targetId, args, LogLevel.DEBUG, logger, methodName, clazzName);

        //fail("unverified");
    }

    @Test
    public void testLogResult_1()
        throws Exception {
        final String targetId = null;
        final Object result = new Object();
        final Logger logger = LoggerFactory.getLogger("");
        final String methodName = "methodName";
        final String clazzName = "ClazzName";

        MethodCallFormatter.logResult(targetId, result, LogLevel.DEBUG, logger, methodName, clazzName);

        //fail("unverified");
    }

    @Test
    public void testLogResult_2()
        throws Exception {
        final String targetId = "";
        final Object result = new Object();
        final Logger logger = LoggerFactory.getLogger("");
        final String methodName = "methodName";
        final String clazzName = "ClazzName";

        MethodCallFormatter.logResult(targetId, result, LogLevel.DEBUG, logger, methodName, clazzName);

        //fail("unverified");
    }

    @Test
    public void testObject2id_1()
        throws Exception {
        final Object o = null;

        final String result = MethodCallFormatter.object2id(o);

        assertEquals(null, result);
    }

    @Test
    public void testObject2id_2()
        throws Exception {
        final Object o = new Object();

        final String result = MethodCallFormatter.object2id(o);

        assertTrue(result!=null);
    }
}