package pd.workshop.neo4j.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RelationshipEntity( type = "relation")
@EqualsAndHashCode(callSuper = false)
public class Relation {
    @GraphId
    private Long graphId;
    @StartNode
    private Master master;
    @EndNode
    private Detail detail;
    private String name;
}
