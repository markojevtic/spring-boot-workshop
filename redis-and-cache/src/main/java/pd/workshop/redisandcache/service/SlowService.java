package pd.workshop.redisandcache.service;

import pd.workshop.redisandcache.domain.Info;

public interface SlowService {
    Info getInfo( String id ) throws InterruptedException;
    Info putInfo( Info info );
    void deleteInfo( String id );
    void deleteAll();
    Info migrateId( Info targetInfo, String newId );
}
