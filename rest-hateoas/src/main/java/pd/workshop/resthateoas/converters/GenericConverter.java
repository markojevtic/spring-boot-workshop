package pd.workshop.resthateoas.converters;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;
import pd.workshop.resthateoas.domain.excpetion.ApplicationException;

public abstract class GenericConverter<SOURCE, RESULT> implements Converter <SOURCE, RESULT> {

    protected static final String CONVERSION_ERROR_MESSAGE = "Type [%s] cannot be converted automatically to type [%s]. "
            + "You must exclude field %s and convert it manually if it's necessary.";

    @Autowired
    protected ConversionService conversionService;

    @Override
    public RESULT convert( SOURCE source ) {
        RESULT result;
        try {
            result = getResultClass().newInstance();
            Set <String> exclude = getExcludeSourceFields();
            for( Field srcField : source.getClass().getDeclaredFields() ) {
                if( !exclude.contains( srcField.getName() ) ) {
                    Field trgField = findField( getResultClass(), srcField.getName() );
                    if( trgField != null ) {
                        setField( trgField, result, convertField( source, srcField, trgField ) );
                    }
                }
            }
        } catch( IllegalAccessException | InstantiationException e ) {
            throw new ApplicationException( "Conversion error!", e );
        }

        return result;
    }

    private Object convertField( SOURCE source, Field srcField, Field trgField ) {
        Object sourceValue;
        makeAccessible( srcField );
        makeAccessible( trgField );
        sourceValue = getField( srcField, source );
        if( !trgField.getType().isInstance( sourceValue ) ) {
            TypeDescriptor sourceType = new TypeDescriptor( srcField );
            TypeDescriptor targetType = new TypeDescriptor( trgField );
            if( conversionService.canConvert( sourceType, targetType ) ) {
                sourceValue = conversionService.convert( sourceValue, sourceType, targetType );
            } else {
                throw new ApplicationException( String.format( CONVERSION_ERROR_MESSAGE,
                        sourceType.getName(), targetType.getName(), srcField.getName() ) );
            }
        }
        return sourceValue;
    }

    protected Set <String> getExcludeSourceFields() {
        return Collections.EMPTY_SET;
    }

    protected abstract Class <RESULT> getResultClass();

}
