package pd.workshop.resthateoas.domain.excpetion;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private final ImmutableMap <String, String> fieldError;

    public ValidationException( String message, Map <String, String> fieldError ) {
        super( message );
        this.fieldError = ImmutableMap.copyOf( fieldError );
    }

    public ImmutableMap <String, String> getFieldError() {
        return fieldError;
    }
}
