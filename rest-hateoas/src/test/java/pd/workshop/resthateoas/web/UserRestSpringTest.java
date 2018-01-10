package pd.workshop.resthateoas.web;

import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pd.workshop.resthateoas.domain.model.User;
import pd.workshop.resthateoas.web.dto.UserDTO;

@SpringBootTest
@RunWith( SpringRunner.class )
public class UserRestSpringTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ConversionService conversionService;

    private MockMvc mvc;

    @Before
    public void setupMvcMock() {
        mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext ).build();
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 13L;
        mvc.perform( delete( UserRest.createLink().toString() + "/" + id )
                .accept( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().isNoContent() );
    }

    @Test
    public void testIsConverterAvailable() {
        assertThat( conversionService ).isNotNull();
        assertThat( conversionService.canConvert( User.class, UserDTO.class ) ).isTrue();
    }

    @Test
    public void testLinks() {
        Link resourcesLink = UserRest.createLink().withSelfRel();
        Link singleResourceLink = UserRest.creatSingleLink( 13L ).withSelfRel();
        assertThat( resourcesLink.getHref() ).endsWith( "/users" );
        assertThat( singleResourceLink.getHref() ).endsWith( "/users/13" );
    }
}
