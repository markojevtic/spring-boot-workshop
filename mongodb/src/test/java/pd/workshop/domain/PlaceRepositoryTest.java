package pd.workshop.domain;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.InvalidMongoDbApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import static pd.workshop.domain.PlaceTestUtil.BELGRADE_LOCATION;
import static pd.workshop.domain.PlaceTestUtil.BUDAPEST_LOCATION;
import static pd.workshop.domain.PlaceTestUtil.MUNICH_LOCATION;
import static pd.workshop.domain.PlaceTestUtil.VIENNA_LOCATION;
import pd.workshop.domain.model.Place;

@RunWith( SpringRunner.class )
@SpringBootTest
public class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceTestUtil placeTestUtil;

    @Before
    public void setUp() {
        placeTestUtil.resetPlaceCollection();
    }

    @Test
    public void testInsertVsStore() {
        Map <Integer, Long[]> resultMap = new TreeMap <>();

        int repetition = 8;
        for( int r = 0; r < repetition; r++ ) {
            for( int i = 10; i <= 100000; i *= 10 ) {
                System.err.println( "repetition: " + r + " size: " + i );
                List <Place> placeList = placeTestUtil.generatePlaces( i );
                Long insertSave[] = resultMap.getOrDefault( i, new Long[] { 0L, 0L } );
                placeRepository.deleteAll();
                long start = System.currentTimeMillis();
                placeRepository.insert( placeList );
                long end = System.currentTimeMillis();
                long insertTime = end - start;
                insertSave[0] += insertTime;

                placeRepository.deleteAll();
                start = System.currentTimeMillis();
                placeRepository.save( placeList );
                end = System.currentTimeMillis();
                long saveTime = end - start;
                insertSave[1] += saveTime;
                resultMap.putIfAbsent( i, insertSave );
            }
        }
        resultMap.forEach( ( count, times ) -> {
            System.err.println( String.format( "%s\t%s\t%s", count, times[0] / repetition, times[1] / repetition ) );
        } );
    }

    @Test
    public void testFindByQueryMethods() {
        placeTestUtil.givenPlaceWithNameAndLocation( "Belgrade", BELGRADE_LOCATION, 101L );
        placeTestUtil.givenPlaceWithNameAndLocation( "Budapest", BUDAPEST_LOCATION, 1L );
        placeTestUtil.givenPlaceWithNameAndLocation( "Vienna", VIENNA_LOCATION, 60L );
        placeTestUtil.givenPlaceWithNameAndLocation( "Munich", MUNICH_LOCATION, 56L );

        List <Place> result = placeRepository.findByName( "Belgrade" );
        assertThat( result ).isNotEmpty();

        result = placeRepository.findTop1ByNameStartingWith( "B" );
        assertThat( result ).hasSize( 1 );

        assertThatThrownBy( () -> placeRepository.findByNameStartingWithAndNameEndingWith( "B", "t" ) )
                .isInstanceOf( InvalidMongoDbApiUsageException.class );

        result = placeRepository.findByVotesWhereItIsEvenNumber();
        assertThat( result ).hasSize( 2 );
    }

    @Test
    public void testVoteCustomInterface() {
        long startVotes = 100L;
        Place place = placeTestUtil.givenPlaceWithNameAndLocation( "Belgrade", BELGRADE_LOCATION, startVotes );
        int count = placeRepository.vote( place.getId() );
        assertThat( count ).isEqualTo( 1 );
        Place placeAfterVote = placeRepository.findOne( place.getId() );
        assertThat( placeAfterVote.getVotes() ).isEqualTo( startVotes + 1 );
    }

}