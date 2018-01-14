package pd.workshop.securityrest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class ProtectedServiceTest {

    @Autowired
    private ProtectedService protectedService;

    @Test
    @WithMockUser( username = "Max", roles = { "ADMIN" } )
    public void secureMethodWorksWithUser() throws Exception {
        assertThat( protectedService.secureMethod() ).isNotBlank();
    }

    @Test
    @WithMockUser( username = "Erika", roles = { "VISITOR" } )
    public void secureMethodThrowsExceptionWhenUserDoesNotHaveAdminRole() throws Exception {
        assertThatThrownBy( () -> protectedService.secureMethod() ).isInstanceOf( AccessDeniedException.class );
    }

}