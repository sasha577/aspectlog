package de._7p.solcon.fit.logging.common.formatters.tostring.common;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public abstract class AbstractPair<T1,T2> implements Serializable{

    cc cc(){
        final Class<?> clazz = null;
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.is


    }
    private final T1 first;
    private final T2 second;

    public AbstractPair(final T1 first, final T2 second) {
        this.first=first;
        this.second=second;
    }

    /**
     * get first object
     * @return first object
     */
    final protected T1 getFirst() {
        return first;
    }

    /**
     * get second object
     * @return second object
     */
    final protected T2 getSecond() {
        return second;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append(first)
        .append(second)
        .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(31,37)
        .append(first)
        .append(second)
        .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        
        // to different classes with the same members are not the same!
        if (obj.getClass() != getClass()) {return false;}

        @SuppressWarnings("rawtypes")
        final AbstractPair rhs = (AbstractPair) obj;
        return new EqualsBuilder()
        .append(first, rhs.first)
        .append(second, rhs.second)
        .isEquals();
    }


}
