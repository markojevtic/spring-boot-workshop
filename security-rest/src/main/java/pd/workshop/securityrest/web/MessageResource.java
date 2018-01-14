package pd.workshop.securityrest.web;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import pd.workshop.securityrest.service.ProtectedService;

@RestController
@RequestMapping(path="/messages", produces = APPLICATION_JSON_UTF8_VALUE)
public class MessageResource {

    @Autowired
    private ProtectedService protectedService;

    @RequestMapping(method = GET)
    public ResponseEntity<List<String>> getPublicMessage() {
        return ResponseEntity.ok( Collections.singletonList( "Public message!" ) );
    }

    @RequestMapping(method = POST )
    public ResponseEntity<String> postProtected() {
        return ResponseEntity.ok( "This is a protected post, only an authenticated user has access!" );
    }

    @RequestMapping(method = GET, path = "/protectedUrl")
    public ResponseEntity<String> getProtectedUrl() {
        return ResponseEntity.ok( "This is a protected url, only an authenticated user has access!" );
    }



    @RequestMapping(method = GET, path = "/serviceProtected")
    public ResponseEntity<String> getServiceProtectedMessage() {
        return ResponseEntity.ok( protectedService.secureMethod() );
    }

    public static Link getMessageLink() {
        return linkTo( methodOn( MessageResource.class ).getPublicMessage() ).withSelfRel();
    }

    public static Link getProtectedUrlLink() {
        return linkTo( methodOn( MessageResource.class ).getProtectedUrl() ).withSelfRel();
    }

    public static Link getServiceProtectedMessageLink() {
        return linkTo( methodOn( MessageResource.class ).getServiceProtectedMessage() ).withSelfRel();
    }

}
