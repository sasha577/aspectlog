package de._7p.solcon.fit.logging.common.formatters.signature;

import de._7p.solcon.fit.logging.common.formatters.tostring.ToStringConverter;

/**
 * collection of formatting utils
 * 
 * @author arubinov
 * 
 */
public final class SignatureFormatter {

    public static String format(final String signature, final Object[] args) {
        return String.format("%s(%s)", signature, ToStringConverter.INSTANCE.printArrayItems(args));
    }

    private SignatureFormatter() {

    }

}
