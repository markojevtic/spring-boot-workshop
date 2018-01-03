package pd.workshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pd.workshop.domain.PlaceRepository;
import pd.workshop.domain.model.Place;
import pd.workshop.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Place storeRepository( Place place ) {
        //do some business logic(for example create tags from description.
        return placeRepository.save( place );
    }
}
