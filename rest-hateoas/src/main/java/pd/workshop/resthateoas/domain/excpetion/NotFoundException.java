package pd.workshop.resthateoas.domain.excpetion;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = NOT_FOUND, reason = "Entity not found!" )
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }
}
