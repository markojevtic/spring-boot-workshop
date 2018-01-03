package pd.workshop.resthateoas.domain.excpetion;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = INTERNAL_SERVER_ERROR, reason = "Server kaputt!")
public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super();
    }
}
