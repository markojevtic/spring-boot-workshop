package pd.workshop.securityrest.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.securityrest.security.JWT;
import pd.workshop.securityrest.security.JWTAuthentication;
import pd.workshop.securityrest.security.JWTService;

@RunWith( SpringRunner.class )
@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService service;

    @Test
    public void testComposeDecompose() throws Exception {
        SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken( "max", "secret" ) );
        JWT jwt = service.composeToken();
        JWTAuthentication result = service.decomposeToken( jwt);
        assertThat( result ).isNotNull();
        assertThat( result.getPrincipal() ).isEqualTo( "max" );
    }
}