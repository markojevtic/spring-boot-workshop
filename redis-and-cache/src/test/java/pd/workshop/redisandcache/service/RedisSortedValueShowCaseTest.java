package pd.workshop.redisandcache.service;

import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest
public class RedisSortedValueShowCaseTest {

    @Autowired
    private ApplicationContext applicationContext;

    private RedisTemplate <String, String> redisTemplate;

    @Before
    public void setUpRedisTemplate() {
        //This is not the right way to initialize redis template, because it not thread safe.
        redisTemplate = applicationContext.getBean( "redisTemplate", RedisTemplate.class );
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer( new GenericToStringSerializer( String.class ) );
    }

    @Test
    public void showSomeSetOps() {
        final String key = "PRODUCT000100_PRICELIST_299";

        //reset test data
        redisTemplate.delete( key );

        ZSetOperations <String, String> valueOps = redisTemplate.opsForZSet();
        valueOps.add( key, "Value-1200", 1200 );
        valueOps.add( key, "Value-1000", 1000 );
        valueOps.add( key, "Value-1100", 1100 );
        Set <String> result = valueOps.reverseRangeByScore( key, 0, 1112, 0L, 1L );

        assertThat( result.size() ).isEqualTo( 1 );
        assertThat( result.iterator().next() ).isEqualTo( "Value-1100" );
    }
}
