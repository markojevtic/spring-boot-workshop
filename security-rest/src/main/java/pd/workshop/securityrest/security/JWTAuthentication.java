package pd.workshop.securityrest.security;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthentication implements Authentication {
    private String name;
    private List <GrantedAuthority> authorities;
    private String credentials;
    private String details;
    private String principal;
    private JWT token;
    private LocalDateTime expireDate;
    private boolean authenticated;
}
