//
//(C) 2002-2009 PSI AG
//
//All rights reserved
//

package de._7p.solcon.fit.logging.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
public @interface Logged {
    
    /**
     * Optional parameter to define the log level for the logger.
     * <p>
     * Default log level is DEBUG.
     */
    public enum LogLevel {
        /**
         * Most specific log usually used during development
         */
        TRACE,
        
        /**
         * More specific log usually used to find bugs
         */
        DEBUG,
        
        /**
         * Specific log usually used to inform about general state changes
         */
        INFO,
        
        /**
         * Log usually used to indicate harmful situations
         */
        WARN,
        
        /**
         * Log usually used to indicate that exceptional cases occurred, but
         * execution can continue
         */
        ERROR,
        
        /**
         * Log usually used to indicate that the execution cannot continue
         */
        FATAL
    }
    
    LogLevel value() default LogLevel.DEBUG;
    
}
