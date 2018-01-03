package pd.workshop.web;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/greetings", produces = APPLICATION_JSON_VALUE )
public class GreetingResource {

    @RequestMapping( method = GET )
    public ResponseEntity <String> sayHello() {
        return new ResponseEntity <>( "Hello world", OK );
    }
}
