package pd.workshop.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/greetings", produces = APPLICATION_JSON_VALUE )
@Api(value = "Greeting resource", description = "All operation related to greeting.")
public class GreetingResource {
    private static final String DEFAULT_MESSAGE = "Hello world.";
    private Optional <String> message = Optional.empty();

    @GetMapping
    public ResponseEntity <MessageDTO> getMessage() {
        return ResponseEntity.ok( new MessageDTO( message.orElse( DEFAULT_MESSAGE ) ) );
    }

    @ApiResponses( {
            @ApiResponse( code = 204, message = "New message has been set.", response = Void.class ),
            @ApiResponse( code = 500, message = "Something has gone wrong on server." ) } )
    @ApiOperation( value = "The message setter.", notes = "This method set message, that later will be returned on get." )
    @PostMapping
    public ResponseEntity <Void> setMessage( String message ) {
        this.message = Optional.ofNullable( message );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity <Void> deleteMessage() {
        this.message = Optional.empty();
        return ResponseEntity.noContent().build();
    }
}
