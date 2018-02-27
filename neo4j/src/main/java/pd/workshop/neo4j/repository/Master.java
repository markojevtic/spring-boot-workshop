package pd.workshop.neo4j.repository;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NodeEntity
@EqualsAndHashCode(exclude = {"relations"})
public class Master {
    @GraphId
    private Long graphId;
    private String name;
    @Relationship(type = "relation")
    private List<Relation> relations;
}
