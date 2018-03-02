package pd.workshop.resthateoas.web;

import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
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

    /**
     * Shows how does exception handlers works, and how to test it.
     * Warning: In real world, you should mock service layer, not to spy the controller.
     */
    @Test
    public void testExceptionHandler() throws Exception {
        doThrow( new NotFoundException() )
                .doThrow( new ApplicationException() )
                .when( userRest ).getUser( anyLong() );

        Long id = 13L;
        mvc.perform( get( UserRest.creatSingleLink( id ).toString() )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().is( NOT_FOUND.value() ) );

        mvc.perform( get( UserRest.creatSingleLink( id ).toString() )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().is( INTERNAL_SERVER_ERROR.value() ) );
    }

    /**
     * Test shows how do custom exception handler and payload work.
     * Warning: In real application do not spy controller, instead mock the services.
     */
    @Test
    public void testCustomHandler() throws Exception {
        String anErrorText = "An error text";
        String fieldName = "testField";
        String theFieldErrorMessage = "the field is not valid";

        doThrow( new ValidationException( anErrorText, Maps.newHashMap( fieldName, theFieldErrorMessage ) ) )
                .when( userRest ).getUser( anyLong() );

        Long id = 13L;
        mvc.perform( get( UserRest.creatSingleLink( id ).toString() )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().is(BAD_REQUEST.value()) )
                .andExpect( jsonPath( "$.message" ).value( anErrorText ) )
                .andExpect( jsonPath( "$.fieldErrors" ).isArray() )
                .andExpect( jsonPath( "$.fieldErrors[0].field" ).value( fieldName ) )
                .andExpect( jsonPath( "$.fieldErrors[0].message" ).value( theFieldErrorMessage ) );

    }

    /**
     * Test shows the difference between exception handlers and programmatically error returning.
     */
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
