package pd.workshop.resthateoas.web;

import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pd.workshop.resthateoas.domain.excpetion.ApplicationException;
import pd.workshop.resthateoas.domain.excpetion.NotFoundException;
import pd.workshop.resthateoas.domain.excpetion.ValidationException;

@RunWith( SpringRunner.class )
@SpringBootTest
public class UserRestExceptionHandlingTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @SpyBean
    private UserRest userRest;

    private MockMvc mvc;

    @Before
    public void setupMvcMock() {
        mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext ).build();
    }

    @Test
    public void testExceptionHandler() throws Exception {
        given( userRest.getUser( anyLong() ) ).willThrow( new NotFoundException(), new ApplicationException() );
        Long id = 13L;
        mvc.perform( get( UserRest.creatSingleLink( id ).toString() )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().is4xxClientError() );

        mvc.perform( get( UserRest.creatSingleLink( id ).toString() )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().is5xxServerError() );
    }

    @Test
    public void testCustomHandler() throws Exception {
        String anErrorText = "An error text";
        String fieldName = "testField";
        String theFieldErrorMessage = "the field is not valid";
        given( userRest.getUser( anyLong() ) ).
                willThrow( new ValidationException( anErrorText, Maps.newHashMap( fieldName, theFieldErrorMessage ) ) );
        Long id = 13L;
        mvc.perform( get( UserRest.creatSingleLink( id ).toString() )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().isInternalServerError() )
                .andExpect( jsonPath( "$.message" ).value( anErrorText ) )
                .andExpect( jsonPath( "$.fieldErrors" ).isArray() )
                .andExpect( jsonPath( "$.fieldErrors[0].field" ).value( fieldName ) )
                .andExpect( jsonPath( "$.fieldErrors[0].message" ).value( theFieldErrorMessage ) );

    }

    @Test
    public void testErrorVsExceptionHandler() throws Exception {
        Long id = 13L;
        Long count = 100000L;
        Long start = System.currentTimeMillis();
        for( int i = 0; i < count; i++ ) {
            mvc.perform( get( UserRest.createLink().toString() + "/" + id + "/error" )
                    .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                    .andExpect( status().is4xxClientError() );
        }
        Long end = System.currentTimeMillis();
        Long errorTime = end - start;
        start = System.currentTimeMillis();
        for( int i = 0; i < count; i++ ) {
            mvc.perform( get( UserRest.createLink().toString() + "/" + id + "/exception" )
                    .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                    .andExpect( status().is4xxClientError() );
        }
        end = System.currentTimeMillis();
        Long exceptionTime = end - start;
        System.err.println( "Error time: " + errorTime );
        System.err.println( "Exception time: " + exceptionTime );
    }

}
