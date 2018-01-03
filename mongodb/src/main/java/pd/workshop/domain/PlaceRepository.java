package pd.workshop.domain;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pd.workshop.domain.model.Place;

public interface PlaceRepository extends MongoRepository <Place, String>, PlaceRepositoryCustom {
    List <Place> findByName( String name );

    List <Place> findTop1ByNameStartingWith( String start );

    List <Place> findByNameStartingWithAndNameEndingWith( String start, String end );

    @Query( "{$where: 'this.votes%2 == 0' }" )
    List <Place> findByVotesWhereItIsEvenNumber();
}
