package pd.workshop.redisandcache.service;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import pd.workshop.redisandcache.domain.Info;

@Service
public class SlowService {
    private static final String CACHE_NAME = "InfoCache";
    Logger logger = Logger.getLogger( SlowService.class );

    @Cacheable( value = CACHE_NAME )
    public Info getInfo( String id ) throws InterruptedException {
        Thread.sleep( 1000 );
        return new Info( id, "Info-" + id );
    }

    @CachePut( value = CACHE_NAME, key = "#result.id" )
    public Info putInfo( Info info ) {
        doSomething();
        return info;
    }

    @CacheEvict( value = CACHE_NAME )
    public void deleteInfo( String id ) {
        doSomething();
    }

    @CacheEvict( value = CACHE_NAME, allEntries = true )
    public void deleteAll() {
        doSomething();
    }

    private void doSomething() {
        logger.warn( "Do something is called" );
    }

    @Caching( evict = @CacheEvict( value = CACHE_NAME, key = "#p0.id" ),
            put = @CachePut( value = CACHE_NAME, key = "#result.id" ) )
    public Info migrateId( Info targetInfo, String newId ) {
        //be careful with mutable arguments and cache(evict,put), it runs after function call
        //i.e. if we do something like targetInfo.setId(newId), cache evict doesn't work properly.
        return new Info( newId, targetInfo.getMessage() );
    }

}
