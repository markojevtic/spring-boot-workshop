package pd.workshop.securityrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity( securedEnabled = true )
public class SecurityRestApplication {

    public static void main( String[] args ) {
        SpringApplication.run( SecurityRestApplication.class, args );
    }
}
