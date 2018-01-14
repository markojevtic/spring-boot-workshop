package pd.workshop.redisandcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisAndCacheApplication {

    public static void main( String[] args ) {
        SpringApplication.run( RedisAndCacheApplication.class, args );
    }
}
