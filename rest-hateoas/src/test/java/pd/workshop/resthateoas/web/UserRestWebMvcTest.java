package pd.workshop.resthateoas.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import pd.workshop.resthateoas.domain.model.User;
import pd.workshop.resthateoas.web.dto.UserDTO;

@RunWith( SpringRunner.class )
@WebAppConfiguration
@WebMvcTest
public class UserRestWebMvcTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @SpyBean
    private UserRest userRest;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ConversionService conversionService;

    @Test
    public void testInsert() throws Exception {

        final UserDTO userDTO = UserDTO.builder()
                .userId( 1L )
                .firstName( "Max" )
                .lastName( "Mustermann" )
                .build();

        mvc.perform( post( UserRest.createLink().toUri() )
                .accept( MediaType.APPLICATION_JSON_UTF8 )
                .contentType( MediaType.APPLICATION_JSON_UTF8 )
                .content( mapper.writeValueAsString( userDTO ) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.userId" ).value( userDTO.getUserId() ) )
                .andExpect( jsonPath( "$.firstName" ).value( userDTO.getFirstName() ) )
                .andExpect( jsonPath( "$.lastName" ).value( userDTO.getLastName() ) );
    }

    @Test
    public void testSearch() throws Exception {
        mvc.perform( get( UserRest.createLink().toString()
                + "?firstName=Max&lastName=Mustermann&page=13&size=23&sort=firstName,asc&sort=lastName,desc" )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().isOk() );
        ArgumentCaptor <Pageable> pageableCaptor = ArgumentCaptor.forClass( Pageable.class );
        verify( userRest ).search( eq( "Max" ), eq( "Mustermann" ), pageableCaptor.capture() );

        Pageable pageable = pageableCaptor.getValue();
        assertThat( pageable.getPageSize() ).isEqualTo( 23 );
        assertThat( pageable.getPageNumber() ).isEqualTo( 12 );
        assertThat( pageable.getSort() ).isNotNull();
        assertThat( pageable.getSort().getOrderFor( "firstName" ).getDirection() ).isEqualTo( Sort.Direction.ASC );
        assertThat( pageable.getSort().getOrderFor( "lastName" ).getDirection() ).isEqualTo( Sort.Direction.DESC );
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 13L;
        mvc.perform( delete( UserRest.createLink().toString() + "/" + id )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().isNoContent() );
        verify( userRest ).delete( eq( id ) );
    }

    @Test
    public void testThatConverterIsNotAvailableInWebMvcTests() {
        assertThat( conversionService ).isNotNull();
        assertThat( conversionService.canConvert( User.class, UserDTO.class ) ).isFalse();
    }

}