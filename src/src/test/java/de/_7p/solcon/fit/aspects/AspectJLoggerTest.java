package de._7p.solcon.fit.aspects;

import org.junit.Test;

import de._7p.solcon.fit.logging.api.annotations.Logged;
import de._7p.solcon.fit.logging.api.annotations.Logged.LogLevel;

/**
 * Around method logger that uses log4j as the underlying logging facility.
 */
public final class AspectJLoggerTest {

    @Test
    @Logged
    public void defaultLog(){
        
    }
    
    @Test
    @Logged(LogLevel.DEBUG)
    public void debugLog(){
        
    }

    @Test
    @Logged(LogLevel.TRACE)
    public void traceLog(){
        
    }

}
