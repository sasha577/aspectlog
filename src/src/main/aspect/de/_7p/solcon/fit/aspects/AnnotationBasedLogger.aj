package de._7p.solcon.fit.aspects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de._7p.solcon.fit.logging.api.annotations.Logged;
import de._7p.solcon.fit.logging.api.annotations.Logged.LogLevel;
import de._7p.solcon.fit.logging.common.LogUtils;
import de._7p.solcon.fit.logging.common.asserts.UncheckedThrow;
import de._7p.solcon.fit.logging.common.formatters.MethodCallFormatter;

public final aspect AnnotationBasedLogger { // NO_UCD

    pointcut loggedCall():  execution(@Logged * *.*(..)) || execution(@Logged *.new(..));

    Object around(): loggedCall() {

        final Signature signature = thisJoinPoint.getStaticPart().getSignature();

        final Class<?> clazz = signature.getDeclaringType();
        final Logged annotation = getAnnotation(signature);

        final String methodName = signature.getName();
        final String clazzName = clazz.getSimpleName();
        LogLevel level = annotation.value();
        Logger logger = LoggerFactory.getLogger(clazz);


        if (LogUtils.isEnabled(logger, level)) {

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

}
