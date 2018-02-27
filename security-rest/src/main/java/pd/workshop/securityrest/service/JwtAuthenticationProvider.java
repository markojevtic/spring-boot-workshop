package pd.workshop.securityrest.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pd.workshop.securityrest.security.JWTAuthentication;
import pd.workshop.securityrest.security.JWTService;

@Service
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JWTService jwtService;

    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
        JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
        try {
            jwtAuthentication = jwtService.decomposeToken( ( (JWTAuthentication) authentication ).getToken() );
        } catch( IOException e ) {
            throw new AuthenticationException( "JWT token is not valid!" ){};
        }
        if(jwtAuthentication.getExpireDate().isAfter( LocalDateTime.now() ) && jwtService.isTokenValid( jwtAuthentication.getToken() )) {
            jwtAuthentication.setAuthenticated( true );
        } else {
            throw new AuthenticationException( "JWT token is not valid!" ){};
        }
        return jwtAuthentication;
    }

    @Override
    public boolean supports( Class <?> authentication ) {
        return JWTAuthentication.class == authentication;
    }
}
