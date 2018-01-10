package pd.workshop.domain.impl;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import pd.workshop.domain.PlaceRepositoryCustom;
import pd.workshop.domain.model.Place;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int vote( String placeId ) {
        Query query = new Query( where( "id" ).is( placeId ) );
        Update update = new Update();
        update.inc( "votes", 1 );
        WriteResult resultResult = mongoTemplate.updateFirst( query, update, Place.class );
        return resultResult.getN();
    }

}
