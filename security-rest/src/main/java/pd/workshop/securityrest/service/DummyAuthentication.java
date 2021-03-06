package pd.workshop.securityrest.service;

import java.util.Collections;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class DummyAuthentication implements AuthenticationProvider {

    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
        String user = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        if( !user.equals( password ) ) {
            throw new BadCredentialsException( "Invalid user name or password!!!" );
        }
        return new UsernamePasswordAuthenticationToken( user, password, Collections.singleton( new SimpleGrantedAuthority( "ADMIN"  ) ));
    }

    @Override
    public boolean supports( Class <?> authentication ) {
        return UsernamePasswordAuthenticationToken.class == authentication;
    }
}
