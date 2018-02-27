package pd.workshop.securityrest.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public JwtFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
            throws ServletException, IOException {
        JWT jwt = JWT.parseRequest( request );
        if( jwt != null ) {
            Authentication authResult = authenticationManager.authenticate( JWTAuthentication.builder().token( jwt ).build() );
            SecurityContextHolder.getContext().setAuthentication( authResult );
        }
        filterChain.doFilter( request, response );
    }
}
