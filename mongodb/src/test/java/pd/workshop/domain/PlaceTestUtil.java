package pd.workshop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import pd.workshop.domain.model.Gender;
import pd.workshop.domain.model.PhotoMeta;
import pd.workshop.domain.model.Place;

@Component
public class PlaceTestUtil {
    public static final Point BELGRADE_LOCATION = new Point( 44.9424453, 20.6234838 );
    public static final Point BUDAPEST_LOCATION = new Point( 47.6130119, 19.3345049 );
    public static final Point VIENNA_LOCATION = new Point( 48.2081743, 16.3738189 );
    public static final Point MUNICH_LOCATION = new Point( 48.1351253, 11.5819805 );

    @Autowired
    private PlaceRepository placeRepository;

    public void resetPlaceCollection() {
        placeRepository.deleteAll();
    }

    public Place givenPlaceWithNameAndLocation( String name, Point location, Long votes ) {
        return placeRepository.insert( Place.builder().name( name ).location( location ).votes( votes ).build() );
    }

    public List <Place> generatePlaces( int count ) {
        List <Place> placeList = new ArrayList <>();
        for( int i = 0; i < count; i++ ) {
            placeList.add(
                    Place.builder()
                            .id( "id" + i )
                            .name( "Place" + i )
                            .description( "Description" + i + ". Sring boot!" )
                            .location( BELGRADE_LOCATION )
                            .votes( 10L )
                            .populationByGenders( new HashMap <Gender, Integer>() {
                                {
                                    put( Gender.FEMALE, 600000 );
                                    put( Gender.MALE, 500000 );
                                }
                            } )
                            .photos( generatePhotos( "Place" + i, 100 ) )
                            .build() );
        }
        return placeList;
    }

    private List <PhotoMeta> generatePhotos( String placeName, int count ) {
        List <PhotoMeta> photoMetas = new ArrayList <>();
        for( int i = 0; i < count; i++ ) {
            photoMetas.add( PhotoMeta.builder().label( "Photo-" + i + " of " + placeName )
                    .url( "https://en.wikipedia.org/wiki/Belgrade#/media/File:St._Sava_Temple.jpg" ).build() );
        }
        return photoMetas;
    }
}
