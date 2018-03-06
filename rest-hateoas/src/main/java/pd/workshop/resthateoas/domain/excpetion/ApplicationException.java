package pd.workshop.resthateoas.domain.excpetion;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = INTERNAL_SERVER_ERROR, reason = "Server kaputt!" )
public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super();
    }

    public ApplicationException( String message ) {
        super( message );
    }

    public ApplicationException( String message, Throwable cause ) {
        super( message, cause );
    }
}
