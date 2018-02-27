package pd.workshop.securityrest.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import static org.springframework.util.StringUtils.isEmpty;

@Value
public class JWT {
    public static final String BEARER = "Bearer ";

    private String token;

    private JWT( String value ) {
        this.token = value;
    }

    public static JWT parseBearer( String bearer ) {
        JWT result = null;
        if( !isEmpty( bearer ) && bearer.startsWith( BEARER ) ) {
            result = new JWT( bearer.replaceFirst( BEARER, "" ) );
        }
        return result;
    }

    public static JWT parseRequest( @NotNull HttpServletRequest request ) {
        return parseBearer( request.getHeader( HttpHeaders.AUTHORIZATION ) );
    }

    public static JWT of( String token ) {
        if( isEmpty( token ) ) {
            return null;
        }
        return new JWT( token );
    }
}
