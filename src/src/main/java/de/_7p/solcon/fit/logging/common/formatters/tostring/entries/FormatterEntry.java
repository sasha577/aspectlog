package de._7p.solcon.fit.logging.common.formatters.tostring.entries;

import de.psi.telco.mccm.common.fp.Transformer;
import de.psi.telco.mccm.common.util.AbstractPair;

@SuppressWarnings("serial")
public final class FormatterEntry<T> extends AbstractPair<Class<T>, Transformer<T, String>>{

    public static <T> FormatterEntry<T> create(final Class<T> clazz, final Transformer<T, String> formatter){
        return new FormatterEntry<T>(clazz, formatter);
    }
    
    private FormatterEntry(final Class<T> clazz, final Transformer<T, String> formatter) {
        super(clazz, formatter);
    }

    public Class<T> getClazz() {
        return super.getFirst();
    }
    
    public Transformer<T, String> getFormatter() {
        return super.getSecond();
    }
}
