package pd.workshop.web;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/", produces = APPLICATION_JSON_VALUE )
public class GreetingResource {

    @RequestMapping( "greetings" )
    public ResponseEntity <String> sayHello() {
        return new ResponseEntity <>( "Hello world", OK );
    }

}
