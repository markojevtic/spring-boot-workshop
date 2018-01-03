package pd.workshop.resthateoas.web;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pd.workshop.resthateoas.domain.excpetion.ValidationException;
import pd.workshop.resthateoas.web.dto.ErrorDTO;
import pd.workshop.resthateoas.web.dto.FieldErrorDTO;

@ControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger( RestExceptionHandler.class );

    @ExceptionHandler( ValidationException.class )
    public ResponseEntity <ErrorDTO> validationException( final ValidationException validationException ) throws Exception {

        logger.error( "Unexpected error occured. Error message: {}", validationException.getMessage(), validationException );

        final ErrorDTO errorDTO = ErrorDTO.builder()
                .message( validationException.getMessage() )
                .fieldErrors( validationException.getFieldError().entrySet().stream()
                        .map( entry -> FieldErrorDTO.builder()
                                .field( entry.getKey() )
                                .message( entry.getValue() )
                                .build() )
                        .collect( Collectors.toList() ) )
                .build();

        return ResponseEntity.status( INTERNAL_SERVER_ERROR ).body( errorDTO );
    }

}
