package pd.workshop.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.domain.model.Place;

@RunWith( SpringRunner.class )
@SpringBootTest
public class PlaceServiceImplTest {
    @Autowired
    private PlaceServiceImpl placeService;

    @Test
    public void testStorePlace() {
        Place newPlace = Place.builder().name( "Belgrade" ).description( "The capital city of Serbia." ).build();
        Place dbPlace = placeService.storeRepository( newPlace );
        assertThat( dbPlace ).isNotNull();
        assertThat( dbPlace.getId() ).isNotBlank();
    }
}