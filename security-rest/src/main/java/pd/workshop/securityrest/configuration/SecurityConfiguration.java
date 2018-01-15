package pd.workshop.securityrest.configuration;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true, prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.httpBasic()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable().headers()
                .and()
                .rememberMe().and()
                .sessionManagement().sessionCreationPolicy( STATELESS )
                .and()
                .authorizeRequests()
                .antMatchers( GET ).permitAll()
                .antMatchers( POST ).authenticated()
                .antMatchers( "/messages/protectedUrl" ).authenticated();
    }

    @Autowired
    private AuthenticationProvider authenticationProvider;

    protected void configure(AuthenticationManagerBuilder authenticationManager) {
        authenticationManager.authenticationProvider( authenticationProvider );
    }
}
