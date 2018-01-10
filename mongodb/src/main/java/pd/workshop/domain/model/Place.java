package pd.workshop.domain.model;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( callSuper = false )
@ToString
public class Place {
    @Id
    private String id;

    private String name;
    private String description;

    @Builder.Default
    private Long votes = 0L;

    @GeoSpatialIndexed
    private Point location;

    private Integer population;
    private Map <Gender, Integer> populationByGenders;
    private List <PhotoMeta> photos;

}
