package pd.workshop.securityrest.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pd.workshop.securityrest.security.JWTService;

@RestController
@RequestMapping(path="/token", produces = APPLICATION_JSON_UTF8_VALUE)
public class TokenResource {
    @Autowired
    private JWTService jwtService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getToken() throws Exception {
        return ResponseEntity.ok(jwtService.composeToken().getToken());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> invalidateToken() {
        jwtService.invalidateToken();
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
