package de._7p.solcon.fit.logging.common;

import org.slf4j.Logger;

import de._7p.solcon.fit.logging.api.annotations.Logged.LogLevel;

public final class LogUtils {

    public static void log(Logger logger, LogLevel level, String msg) {
        
        if(level.equals(LogLevel.TRACE)){
            logger.trace(msg);
        }else if(level.equals(LogLevel.DEBUG)){
            logger.debug(msg);
        }else if(level.equals(LogLevel.WARN)){
            logger.warn(msg);
        }else if(level.equals(LogLevel.INFO)){
            logger.info(msg);
        }else if(level.equals(LogLevel.ERROR)){
            logger.error(msg);
        }else{
            logger.trace(msg);
        }
    }

    
    public static boolean isEnabled(Logger logger, LogLevel logLevel){

        final LogLevel loggerLevel = getLoglevel(logger);
        return (loggerLevel != null) && loggerLevel.higherOrEqualsThan(logLevel);

    }

    private static LogLevel getLoglevel(final Logger logger) {

        if(logger.isTraceEnabled()){
            return LogLevel.TRACE;
        }else if(logger.isDebugEnabled()){
            return LogLevel.DEBUG;
        }else if(logger.isWarnEnabled()){
            return LogLevel.WARN;
        }else if(logger.isInfoEnabled()){
            return LogLevel.INFO;
        }else if(logger.isErrorEnabled()){
            return LogLevel.ERROR;
        }else{ 
            return null;
        }
    }

}
