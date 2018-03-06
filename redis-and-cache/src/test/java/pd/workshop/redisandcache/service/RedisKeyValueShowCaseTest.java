package pd.workshop.redisandcache.service;

import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest
public class RedisKeyValueShowCaseTest {

    @Autowired
    private ApplicationContext applicationContext;

    private RedisTemplate<String, Long> redisTemplate;

    @Before
    public void setUpRedisTemplate() {
        redisTemplate = applicationContext.getBean( "redisTemplate", RedisTemplate.class );
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer( new GenericToStringSerializer( Long.class ) );
    }

    @Test
    public void testSetIncrementSetOpsAndSetIfAbsent() {
        final String key = "Counter";
        final Long start = 100L;

        ValueOperations valueOps = redisTemplate.opsForValue();
        valueOps.set( key, start );
        valueOps.increment( key, 1L );
        assertThat( valueOps.get( key ) ).isEqualTo( start + 1 );

        assertThat( valueOps.setIfAbsent( key, 200L ) ).isFalse();
    }

    @Test
    public void testSetAndTTL() throws InterruptedException {
        final String key = "Expire";
        ValueOperations valueOps = redisTemplate.opsForValue();
        valueOps.set( key, 1L );
        redisTemplate.expire( key, 3, TimeUnit.SECONDS );
        Thread.sleep( 1501L );
        assertThat( valueOps.get( key ) ).isNotNull();
        Thread.sleep( 1501L );
        assertThat( valueOps.get( key ) ).isNull();
    }
}
