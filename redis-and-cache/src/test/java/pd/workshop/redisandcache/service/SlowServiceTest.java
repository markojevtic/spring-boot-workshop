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
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.redisandcache.domain.Info;

@RunWith( SpringRunner.class )
@SpringBootTest
public class SlowServiceTest {

    @SpyBean
    private SlowService slowService;

    @Before
    public void resetCache() {
        slowService.deleteAll();
    }

    @Test
    public void testCacheable() throws Exception {
        String testId = "testId";
        Info result = slowService.getInfo( testId );
        assertThat( result ).isNotNull();
        assertThat( result.getId() ).isEqualTo( testId );

        //second try
        Info cachedResult = slowService.getInfo( testId );
        assertThat( cachedResult ).isEqualTo( result );
        verify( slowService, times( 1 ) ).getInfo( any() );
    }

    @Test
    public void testCachePutAndCacheable() throws Exception {
        String testId = "testId";
        Info testInfo = new Info( testId, "First put." );
        Info result = slowService.putInfo( testInfo );

        Info cachedResult = slowService.getInfo( testId );
        assertThat( cachedResult ).isEqualTo( result );

        testInfo.setMessage( "The second put" );

        result = slowService.putInfo( testInfo );
        assertThat( result.getMessage() ).isEqualTo( testInfo.getMessage() );
        cachedResult = slowService.getInfo( testId );
        assertThat( cachedResult ).isEqualTo( result );

        verify( slowService, never() ).getInfo( any() );
        verify( slowService, times( 2 ) ).putInfo( any() );
    }

    @Test
    public void testCacheEvictAndCacheable() throws Exception {
        String testId = "testId";
        slowService.getInfo( testId );
        slowService.deleteInfo( testId );
        slowService.getInfo( testId );
        verify( slowService, times( 2 ) ).getInfo( any() );
    }

    @Test
    public void testCacheEvictAllAndCacheable() throws Exception {
        String testId = "testId";
        String secondId = "secondId";

        //following code creates 2 cache entries
        slowService.getInfo( testId );
        slowService.getInfo( secondId );

        //evict all entries from InfoCache
        slowService.deleteAll();

        slowService.getInfo( testId );
        slowService.getInfo( secondId );

        //because of eviction all keys, from target cashe, we have 4 calls instead of 2
        verify( slowService, times( 4 ) ).getInfo( any() );
    }

    @Test
    public void testCachingEvictFunctionality() throws Exception {
        String testId = "testId";
        String newId = "newId";

        Info testInfo = slowService.getInfo( testId );//first call

        slowService.migrateId( testInfo, newId );

        slowService.getInfo( testId ); //it makes second call, because migrate evicted cache for key testId.

        //because of eviction all keys, from target cashe, we have 4 calls instead of 2
        verify( slowService, times( 2 ) ).getInfo( any() );
    }

    @Test
    public void testCachingPutFunctionality() throws Exception {
        String testId = "testId";
        String newId = "newId";

        Info testInfo = new Info( testId, "Bla!" );

        slowService.migrateId( testInfo, newId );

        slowService.getInfo( newId ); //it gets result from cache

        verify( slowService, never() ).getInfo( any() );
    }

}