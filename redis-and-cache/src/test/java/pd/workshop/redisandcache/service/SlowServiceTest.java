package pd.workshop.redisandcache.service;

import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.redisandcache.domain.Info;

@RunWith( SpringRunner.class )
@SpringBootTest
@EnableCaching(proxyTargetClass = true)
public class SlowServiceTest {

    @SpyBean
    private SlowService slowServiceImpl;

    @Before
    public void resetCache() {
        slowServiceImpl.deleteAll();
    }

    @Test
    public void testCacheable() throws Exception {
        String testId = "testId";
        Info result = slowServiceImpl.getInfo( testId );
        assertThat( result ).isNotNull();
        assertThat( result.getId() ).isEqualTo( testId );

        //second try
        Info cachedResult = slowServiceImpl.getInfo( testId );
        assertThat( cachedResult ).isEqualTo( result );
        verify( slowServiceImpl, times( 1 ) ).getInfo( any() );
    }

    @Test
    public void testCachePutAndCacheable() throws Exception {
        String testId = "testId";
        Info testInfo = new Info( testId, "First put." );
        Info result = slowServiceImpl.putInfo( testInfo );

        Info cachedResult = slowServiceImpl.getInfo( testId );
        assertThat( cachedResult ).isEqualTo( result );

        testInfo.setMessage( "The second put" );

        result = slowServiceImpl.putInfo( testInfo );
        assertThat( result.getMessage() ).isEqualTo( testInfo.getMessage() );
        cachedResult = slowServiceImpl.getInfo( testId );
        assertThat( cachedResult ).isEqualTo( result );

        verify( slowServiceImpl, never() ).getInfo( any() );
        verify( slowServiceImpl, times( 2 ) ).putInfo( any() );
    }

    @Test
    public void testCacheEvictAndCacheable() throws Exception {
        String testId = "testId";
        slowServiceImpl.getInfo( testId );
        slowServiceImpl.deleteInfo( testId );
        slowServiceImpl.getInfo( testId );
        verify( slowServiceImpl, times( 2 ) ).getInfo( any() );
    }

    @Test
    public void testCacheEvictAllAndCacheable() throws Exception {
        String testId = "testId";
        String secondId = "secondId";

        //following code creates 2 cache entries
        slowServiceImpl.getInfo( testId );
        slowServiceImpl.getInfo( secondId );

        //evict all entries from InfoCache
        slowServiceImpl.deleteAll();

        slowServiceImpl.getInfo( testId );
        slowServiceImpl.getInfo( secondId );

        //because of eviction all keys, from target cashe, we have 4 calls instead of 2
        verify( slowServiceImpl, times( 4 ) ).getInfo( any() );
    }

    @Test
    public void testCachingEvictFunctionality() throws Exception {
        String testId = "testId";
        String newId = "newId";

        Info testInfo = slowServiceImpl.getInfo( testId );//first call

        slowServiceImpl.migrateId( testInfo, newId );

        slowServiceImpl.getInfo( testId ); //it makes second call, because migrate evicted cache for key testId.

        //because of eviction all keys, from target cashe, we have 4 calls instead of 2
        verify( slowServiceImpl, times( 2 ) ).getInfo( any() );
    }

    @Test
    public void testCachingPutFunctionality() throws Exception {
        String testId = "testId";
        String newId = "newId";

        Info testInfo = new Info( testId, "Bla!" );

        slowServiceImpl.migrateId( testInfo, newId );

        slowServiceImpl.getInfo( newId ); //it gets result from cache

        verify( slowServiceImpl, never() ).getInfo( any() );
    }

}