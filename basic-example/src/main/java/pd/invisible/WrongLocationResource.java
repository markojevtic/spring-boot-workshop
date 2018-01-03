package pd.invisible;

import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wrong")
public class WrongLocationResource {

    @RequestMapping
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity <>( "Hello from wrong location.", OK);
    }
}
