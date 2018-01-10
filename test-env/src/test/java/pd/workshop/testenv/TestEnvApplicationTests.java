package pd.workshop.testenv;

import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest
public class TestEnvApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate <String, String> redisTemplate;

    @Test
    public void testMongo() {
        assertThat( mongoTemplate.getDb() ).isNotNull();
    }

    @Test
    public void testRedis() {
        String messageKey = "message";
        assertThat( redisTemplate.opsForValue().setIfAbsent( messageKey, "Hello world" ) ).isTrue();
        redisTemplate.delete( messageKey );
    }

}
