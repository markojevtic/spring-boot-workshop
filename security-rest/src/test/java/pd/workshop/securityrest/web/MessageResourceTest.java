package pd.workshop.securityrest.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static pd.workshop.securityrest.web.MessageResource.getMessageLink;
import static pd.workshop.securityrest.web.MessageResource.getProtectedUrlLink;
import static pd.workshop.securityrest.web.MessageResource.getServiceProtectedMessageLink;

@SpringBootTest
@RunWith( SpringRunner.class )
public class MessageResourceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setupMvcMock() {
        mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext ).apply( springSecurity() ).build();
    }

    @Test
    @WithMockUser
    public void testPostMessageWithAuthorizedUserReturnsOkStatus() throws Exception {
        mvc.perform( post( getMessageLink().getHref() ) )
                .andExpect( status().isOk() );
    }

    @Test
    public void testPostMessageWithUnauthorizedUserReturnsUnauthorizedStatus() throws Exception {
        mvc.perform( post( getMessageLink().getHref() ) )
                .andExpect( status().isUnauthorized() );
    }

    @Test
    public void testGetMessageReturnsOkStatus() throws Exception {
        mvc.perform( get( getMessageLink().getHref() ) )
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser
    public void testGetProtectedUrlWithAuthorizedUserReturnsOkStatus() throws Exception {
        mvc.perform( get( getProtectedUrlLink().getHref() ) )
                .andExpect( status().isOk() );
    }

    @Test
    public void testGetProtectedUrlWithUnauthorizedUserReturnsUnauthorizedStatus() throws Exception {
        mvc.perform( post( getProtectedUrlLink().getHref() ) )
                .andExpect( status().isUnauthorized() );
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testGetServiceProtectedWithAuthorizedUserReturnsOkStatus() throws Exception {
        mvc.perform( get( getServiceProtectedMessageLink().getHref() ) )
                .andExpect( status().isOk() );
    }

    @Test
    public void testGetServiceProtectedWithUnauthorizedUserReturnsUnauthorizedStatus() throws Exception {
        mvc.perform( get( getServiceProtectedMessageLink().getHref() ) )
                .andExpect( status().isUnauthorized() );
    }

}