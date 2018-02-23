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
    public enum LogLevel{

        /**
         * Log usually used to indicate that exceptional cases occurred, but
         * execution can continue
         */
        ERROR(1),
       
        /**
         * Log usually used to indicate harmful situations
         */
        WARN(2),
        
        /**
         * Specific log usually used to inform about general state changes
         */
        INFO(3),
        
        /**
         * More specific log usually used to find bugs
         */
        DEBUG(4),

        /**
         * Most specific log usually used during development
         */
        TRACE(5);
        
        
        
        private final int level;
        
        private LogLevel(final int level) {
            this.level = level;
        }
        
        public final boolean higherOrEqualsThan(final LogLevel o) {
            return (this.level - o.level) >= 0;
        }
        
    }
    
    LogLevel value() default LogLevel.DEBUG;
    
}
