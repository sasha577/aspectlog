package de._7p.solcon.fit.logging.common.formatters;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de._7p.solcon.fit.logging.common.formatters.signature.SignatureFormatter;
import de._7p.solcon.fit.logging.common.formatters.tostring.ToStringConverter;

/**
 * Around method logger that uses log4j as the underlying logging facility.
 */
public final class MethodCallFormatter {
    
    public static void logInvoke(
            final String targetId, final Object[] args, final Level level, final Logger logger, final String methodName,
        final String clazzName) {
            final String methodWithArgs = SignatureFormatter.format(methodName, args);
            final String invokeMessage = (targetId != null) ?
                    String.format(INVOKE_FORMAT, clazzName, targetId, methodWithArgs)
                    :String.format(INVOKE_FORMAT_STATIC, clazzName,  methodWithArgs);
            logger.log(level, invokeMessage);
    }
    
    public static void logResult(
            final String targetId, final Object result, final Level level, final Logger logger, final String methodName,
        final String clazzName) {
            final String resultMessage = (targetId != null) ?
                    String.format(RESULT_FORMAT, clazzName, targetId, methodName,ToStringConverter.INSTANCE.format(result))
                    :String.format(RESULT_FORMAT_STATIC, clazzName, methodName,ToStringConverter.INSTANCE.format(result));
            logger.log(level, resultMessage);
    }
    
    public static void logException(
            final String targetId, final Throwable e, final Level level, final Logger logger, final String methodName,
        final String clazzName) {
            final String exceptionMessage = (targetId != null) ?
                    String.format(EXCEPTION_FORMAT, clazzName, targetId, methodName, e.getClass().getSimpleName(), e.getMessage())
                    :String.format(EXCEPTION_FORMAT_STATIC, clazzName, methodName, e.getClass().getSimpleName(), e.getMessage());
            logger.log(level, exceptionMessage);
    }
    
    public static String object2id(final Object o){
        return (o!=null) ? Integer.toHexString(System.identityHashCode(o)) :null;
    }
    
    private MethodCallFormatter() {
    }

    private static final String EXCEPTION_FORMAT = "%s[%s].%s throws %s(\u00AB%s\u00BB)";
    private static final String RESULT_FORMAT = "%s[%s].%s return \u00AB%s\u00BB";
    private static final String INVOKE_FORMAT = "%s[%s].%s";

    private static final String EXCEPTION_FORMAT_STATIC = "%s.%s throws %s(\u00AB%s\u00BB)";
    private static final String RESULT_FORMAT_STATIC = "%s.%s return \u00AB%s\u00BB";
    private static final String INVOKE_FORMAT_STATIC = "%s.%s";

}
