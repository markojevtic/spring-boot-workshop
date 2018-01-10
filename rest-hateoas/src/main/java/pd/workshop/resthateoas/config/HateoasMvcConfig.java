package pd.workshop.resthateoas.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.HateoasSortHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class HateoasMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers( List <HandlerMethodArgumentResolver> argumentResolvers ) {
        PageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver(
                new HateoasSortHandlerMethodArgumentResolver() );
        resolver.setOneIndexedParameters( true );
        argumentResolvers.add( resolver );
        super.addArgumentResolvers( argumentResolvers );
    }
}
