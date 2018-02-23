package de._7p.solcon.fit.logging.common.formatters.tostring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de._7p.solcon.fit.logging.common.formatters.tostring.common.Pair;
import de._7p.solcon.fit.logging.common.formatters.tostring.entries.FormatterEntry;

/**
 * converts object to string
 * @author arubinov
 *
 */
public final class ToStringConverter implements Function<Object,String> {


    public static final ToStringConverter INSTANCE = 
        new ToStringConverter(new ArrayCutter(10));

    public static ToStringConverter create(final int collLimit){ // NO_UCD (test only)
        return new ToStringConverter(new ArrayCutter(collLimit));
    }
    
    @SuppressWarnings("unchecked")
    public void registerEntry(final FormatterEntry<?> entry){
        formatterRegistry.put(entry.getClazz(), (Function<Object, String>)entry.getFormatter());
    }

    public void registerFormatters(final Collection<FormatterEntry<?>> entries){

        for(final FormatterEntry<?> e : entries){
            registerEntry(e);
        }
    }

    @Override
    public String apply(final Object o) {
        return format(o);
    }

    public String format(final Object o) {
        return o != null ? getFormatter(o.getClass()).apply(o) : "null";
    }

    public String format(final Object[] args){
        return args != null ? String.format("[%s]", printArrayItems(args)) : "null";
    }

    public String printArrayItems(final Object[] args) {

        final Collection<String> params =
                Arrays.stream(cutter.cut(args)).map(this).map(SUROUND).collect(Collectors.toList());


        final String arrString = StringUtils.join(params,',');

        if(cutter.exceedMaxSize(args)){
            return arrString + ", ...";
        }else{
            return arrString;
        }
    }

    private ToStringConverter(final ArrayCutter cutter) {
        this.cutter = cutter;
        arrayFormatter = createArrayFormatter(this);
        formatterRegistry = createFormatterRegistry(this,cutter);
    }

    private final ArrayCutter cutter;
    private final Function<Object, String> arrayFormatter;

    private final Map<Class<?>, Function<Object, String>> formatterRegistry;

    private static final Function<String,String> SUROUND = new Function<String, String>() {

        @Override
        public String apply(final String o) {
            return String.format("\u00AB%s\u00BB",o);
        }
    };

    private Function<Object,String> getFormatter(final Class<?> key){
        if(key.isArray()){
            return arrayFormatter;
        }
        else{
            final Function<Object, String> formatter = findFormatterByClass(key);
            return formatter != null ? formatter : TO_STRING_FORMATTER;
        }
    }

    private Function<Object, String> findFormatterByClass(final Class<?> key) {
        for(final Map.Entry<Class<?>, Function<Object, String>> c: formatterRegistry.entrySet()){
            final Class<?> registredType = c.getKey();
            if(registredType.isAssignableFrom(key)){
                return c.getValue();
            }
        }
        return null;
    }

    private static final Function<Object, String> createArrayFormatter(final ToStringConverter toStringConverter){ return new Function<Object,String>(){@Override
    public String apply(final Object o) {
        final Class<?> componentType = o.getClass().getComponentType();
        if( !componentType.isPrimitive() ) {
            return toStringConverter.format((Object[])o);
        } else if(componentType == boolean.class ) {
            return toStringConverter.format(ArrayUtils.toObject((boolean[])o));
        } else if(componentType == byte.class ) {
            return toStringConverter.format(ArrayUtils.toObject((byte[])o));
        } else if(componentType == char.class ) {
            return toStringConverter.format(ArrayUtils.toObject((char[])o));
        } else if(componentType == short.class ) {
            return toStringConverter.format(ArrayUtils.toObject((short[])o));
        } else if(componentType == int.class ) {
            return toStringConverter.format(ArrayUtils.toObject((int[])o));
        } else if(componentType == long.class ) {
            return toStringConverter.format(ArrayUtils.toObject((long[])o));
        } else if(componentType == float.class ) {
            return toStringConverter.format(ArrayUtils.toObject((float[])o));
        } else if(componentType == double.class ) {
            return toStringConverter.format(ArrayUtils.toObject((double[])o));
        } else {
            throw new LoggerRuntimeException("unknow primitive type "+componentType);
        }
    }};
    }

    @SuppressWarnings("serial")
    private static final Map<Class<?>, Function<Object, String>> createFormatterRegistry(
        final ToStringConverter toStringConverter,
        final ArrayCutter cutter){

        return
        new HashMap<Class<?>, Function<Object, String>>(){{

            put(Collection.class, new Function<Object, String>(){
                @Override
                @SuppressWarnings("unchecked")
                public String apply(final Object o) {
                    final Collection<Object> arg = (Collection<Object>)o;
                    final Collection<Object> col = cutter.cut(arg);
                    if(cutter.exceedMaxSize(arg)){
                        col.add("...");
                    }
                    return collection2String(col,toStringConverter);
                }
            }
            );

            put(Pair.class, new Function<Object, String>(){
                @Override
                public String apply(final Object o) {
                    final Pair<?,?> pair = (Pair<?,?>)o;
                    return new ToStringBuilder(pair, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append(toStringConverter.apply(pair.getLeft()))
                    .append(toStringConverter.apply(pair.getRight()))
                    .toString();}}
            );


        }};
    }

    private static String collection2String(final Collection<Object> col, final ToStringConverter toStringConverter) {
        return String.format("{%s}", StringUtils.join(col.stream().map(toStringConverter).collect(Collectors.toList())), ',');
    }

    private static final Function<Object, String> TO_STRING_FORMATTER = new Function<Object, String>() {
        @Override
        public String apply(final Object o) {

            final String nameStartsWith = o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
            final String oToString = o.toString();
            if ((oToString != null) && oToString.startsWith(nameStartsWith)) {
                return removePackages(o) + oToString.substring(nameStartsWith.length());
            } else {
                return oToString;
            }

        }
    };

    private static String removePackages(final Object o) {
        return o.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }
    
    private static final class ArrayCutter{
        
        private static final int UNLIMIT_MAXARRAY_LENGTH = -1;
        private final int maxArrayLength;

        public ArrayCutter(final int maxArrayLength) {
            this.maxArrayLength = maxArrayLength;
        }

        public Object[] cut(final Object[] arr){
            if(isUnlimit() || arr==null || arr.length<=maxArrayLength){
                return arr;
            }
            return ArrayUtils.subarray(arr, 0, maxArrayLength);
        }
        
        public boolean exceedMaxSize(final Object[] arr){
            return !isUnlimit() && arr != null && arr.length > maxArrayLength;
        }

        @SuppressWarnings("rawtypes")
        public boolean exceedMaxSize(final Collection arr){
            return !isUnlimit() && arr != null && arr.size() > maxArrayLength;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        private Collection cut(final Collection<?> coll){
            if(isUnlimit() || coll==null || coll.size()<=maxArrayLength){
                return coll;
            }
            final Collection result = new ArrayList(maxArrayLength);
            final Iterator<?> iterator = coll.iterator();
            for(int i = 0; i < maxArrayLength && iterator.hasNext(); ++i){
                result.add(iterator.next());
            }
            return result;
        }
        
        private boolean isUnlimit(){
            return maxArrayLength==UNLIMIT_MAXARRAY_LENGTH;
        }
    }
    
    @SuppressWarnings("serial")
    private static final class LoggerRuntimeException extends RuntimeException{
        public LoggerRuntimeException(final String cause) {
            super(cause);
        }
    }
}
