package pd.workshop.resthateoas.web;

import java.util.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pd.workshop.resthateoas.domain.excpetion.NotFoundException;
import pd.workshop.resthateoas.web.dto.UserDTO;

@RestController
@RequestMapping( value = "/users", produces = APPLICATION_JSON_UTF8_VALUE )
public class UserRest {

    public static final ControllerLinkBuilder createLink() {
        return linkTo( UserRest.class );
    }

    public static final ControllerLinkBuilder createSingleLink( Long id ) {
        return linkTo( methodOn( UserRest.class ).getUser( id ) );
    }

    @RequestMapping( method = GET )
    public PagedResources <UserDTO> search( @RequestParam( required = false ) String firstName,
            @RequestParam( required = false ) String lastName,
            @PageableDefault( size = 25, page = 0, sort = "lastName", direction = Sort.Direction.DESC ) Pageable pageable ) {

        return new PagedResources <>( Collections.EMPTY_LIST, new PagedResources.PageMetadata( 10, 1, 100 ) );
    }

    @RequestMapping( method = GET, path = "/{id}" )
    public ResponseEntity <UserDTO> getUser( @PathVariable Long id ) {
        UserDTO userDto = UserDTO.builder().userId( id ).build();
        userDto.add( createSingleLink( id ).withSelfRel() );
        return ResponseEntity.ok( userDto );
    }

    @RequestMapping( method = POST )
    public ResponseEntity <UserDTO> insert( @RequestBody UserDTO user ) {
        return ResponseEntity.ok( user );
    }

    @RequestMapping( method = PUT )
    public ResponseEntity <UserDTO> update( @RequestBody UserDTO user ) {
        return ResponseEntity.ok( user );
    }

    @RequestMapping( method = DELETE, path = "/{id}" )
    public ResponseEntity <Void> delete( @PathVariable Long id ) {
        return ResponseEntity.noContent().build();
    }

    @RequestMapping( method = GET, path = "/{id}/error" )
    public ResponseEntity <Void> error( @PathVariable Long id ) {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping( method = GET, path = "/{id}/exception" )
    public ResponseEntity <Void> exception( @PathVariable Long id ) {
        throw new NotFoundException();
    }
}

