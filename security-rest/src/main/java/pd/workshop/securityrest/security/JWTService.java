package pd.workshop.securityrest.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private static final String SECRET = "The secret key could be loaded from somewhere";
    private static final int EXPIRE_IN = 36_000_000;
    private static final Set<String> invalidTokens = Collections.synchronizedSet( new HashSet <>( ) );

    public JWT composeToken() throws JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JWT token = null;
        if( auth instanceof UsernamePasswordAuthenticationToken ) {
            token = createToken( (UsernamePasswordAuthenticationToken) auth );
        } else if( auth instanceof JWTAuthentication ) {
            token = ( (JWTAuthentication) auth ).getToken();
        }
        return token;
    }

    private JWT createToken( UsernamePasswordAuthenticationToken auth ) throws JsonProcessingException {
        return JWT.of( Jwts.builder()
                .setSubject( (String) auth.getPrincipal() )
                .setExpiration( new Date( System.currentTimeMillis() + EXPIRE_IN ) )
                .signWith( SignatureAlgorithm.HS512, SECRET.getBytes() )
                .compact() );
    }

    public JWTAuthentication decomposeToken( JWT token ) throws IOException {
        Jwt result = Jwts.parser()
                .setSigningKey( SECRET.getBytes() )
                .parse( token.getToken() );
        DefaultClaims claims = (DefaultClaims) result.getBody();
        return JWTAuthentication.builder()
                .principal( claims.getSubject() )
                .token( token )
                .expireDate( ( (DefaultClaims) result.getBody() ).getExpiration().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime() )
                .build();
    }

    public void invalidateToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JWT token = null;
        if( auth instanceof JWTAuthentication ) {
            token = ( (JWTAuthentication) auth ).getToken();
            invalidTokens.add( token.getToken() );
        }
    }

    public boolean isTokenValid(JWT jwt) {
        return !invalidTokens.contains( jwt.getToken() );
    }
}
