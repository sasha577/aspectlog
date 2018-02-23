package de._7p.solcon.fit.aspects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import de._7p.solcon.fit.logging.api.annotations.Logged;
import de._7p.solcon.fit.logging.api.annotations.Logged.LogLevel;
import de._7p.solcon.fit.logging.common.asserts.UncheckedThrow;
import de._7p.solcon.fit.logging.common.formatters.MethodCallFormatter;
import de.psi.telco.mccm.common.fp.Transformer;
import de.psi.telco.mccm.common.fp.TransformerFactory;

public final aspect AnnotationBasedLogger { // NO_UCD

    pointcut loggedCall():  execution(@Logged * *.*(..)) || execution(@Logged *.new(..));

    Object around(): loggedCall() {

        final Signature signature = thisJoinPoint.getStaticPart().getSignature();

        final Class<?> clazz = signature.getDeclaringType();
        final Logged annotation = getAnnotation(signature);

        final String methodName = signature.getName();
        final String clazzName = clazz.getSimpleName();
        final Level level = LEVEL_2_LOG4J.transform(annotation.value());
        final Logger logger = Logger.getLogger(clazz);


        if (logger.isEnabledFor(level)) {

            final Object[] args = thisJoinPoint.getArgs();
            final String targetIdent = MethodCallFormatter.object2id(thisJoinPoint.getTarget());

            MethodCallFormatter.logInvoke(targetIdent, args, level, logger, methodName, clazzName);
            try {
                final Object result = proceed();
                MethodCallFormatter.logResult(targetIdent, result, level, logger, methodName, clazzName);
                return result;
            }
            catch (Throwable e) {
                MethodCallFormatter.logException(targetIdent, e, level, logger, methodName, clazzName);
                throw UncheckedThrow.throwUnchecked(e);
            }
        }else{
            return proceed();
        }
    }

    private static Logged getAnnotation(final Signature signature){
        if(signature instanceof MethodSignature){
            final Method method = ((MethodSignature) signature).getMethod();
            return method.getAnnotation(Logged.class);
        }else if(signature instanceof ConstructorSignature){
            final Constructor<?> method = ((ConstructorSignature)signature).getConstructor();
            return (Logged)method.getAnnotation(Logged.class);
        }else{
            throw new RuntimeException("unexpected type:"+signature);
        }

    }

    @SuppressWarnings("serial")
    private static final Transformer<LogLevel, Level> LEVEL_2_LOG4J = TransformerFactory
    .mapAdapter(AnnotationBasedLogger.class.getSimpleName(), new HashMap<LogLevel, Level>() {
        {
            put(LogLevel.TRACE, Level.TRACE);
            put(LogLevel.DEBUG, Level.DEBUG);
            put(LogLevel.WARN, Level.WARN);
            put(LogLevel.INFO, Level.INFO);
            put(LogLevel.ERROR, Level.ERROR);
            put(LogLevel.FATAL, Level.FATAL);
        }
    });

}
