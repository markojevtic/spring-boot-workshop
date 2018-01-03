package pd.workshop.domain;

import com.mongodb.DBObject;
import java.util.List;

public interface PlaceRepositoryCustom {
    int vote(String placeId);
}
