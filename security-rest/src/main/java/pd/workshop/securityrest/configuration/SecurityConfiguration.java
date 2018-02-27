package pd.workshop.securityrest.configuration;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pd.workshop.securityrest.security.JwtFilter;
import pd.workshop.securityrest.service.DummyAuthentication;
import pd.workshop.securityrest.service.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true, prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DummyAuthentication dummyAuthentication;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManager) {
        authenticationManager.authenticationProvider( dummyAuthentication ).authenticationProvider( jwtAuthenticationProvider );
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        //@formatter:off
        http.httpBasic()
                .and()
                    .headers().frameOptions().disable()
                .and()
                .   csrf().disable().headers()
                .and()
                    .rememberMe()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy( STATELESS )
                .and()
                    .authorizeRequests()
                    //Warning: orders of matchers is very important,
                    // the first one that matches url is applied.
                    .antMatchers( "/messages/protectedUrl").authenticated()
                    .antMatchers( "/token").authenticated()
                    .antMatchers( POST ).authenticated()
                    .antMatchers( GET ).permitAll();
        //@formatter:off
        http.addFilterBefore( new JwtFilter(authenticationManager()), BasicAuthenticationFilter.class );
    }


}
