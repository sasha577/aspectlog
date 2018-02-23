package de._7p.solcon.fit.logging.common.formatters.tostring.entries;


import de._7p.solcon.fit.logging.common.formatters.tostring.common.AbstractPair;

import java.util.function.Function;

@SuppressWarnings("serial")
public final class FormatterEntry<T> extends AbstractPair<Class<T>, Function<T, String>> {

    public static <T> FormatterEntry<T> create(final Class<T> clazz, final Function<T, String> formatter){
        return new FormatterEntry<T>(clazz, formatter);
    }
    
    private FormatterEntry(final Class<T> clazz, final Function<T, String> formatter) {
        super(clazz, formatter);
    }

    public Class<T> getClazz() {
        return super.getFirst();
    }
    
    public Function<T, String> getFormatter() {
        return super.getSecond();
    }
}
