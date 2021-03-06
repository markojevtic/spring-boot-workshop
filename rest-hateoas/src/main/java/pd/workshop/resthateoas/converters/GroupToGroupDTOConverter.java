package pd.workshop.resthateoas.converters;

import java.util.Collections;
import java.util.Set;
import org.springframework.stereotype.Component;
import pd.workshop.resthateoas.domain.model.Group;
import pd.workshop.resthateoas.web.dto.GroupDTO;

@Component
public class GroupToGroupDTOConverter extends GenericConverter<Group, GroupDTO> {

    @Override
    protected Class <GroupDTO> getResultClass() {
        return GroupDTO.class;
    }

    @Override
    protected Set<String> getExcludeSourceFields() {
        return Collections.singleton( "other" );
    }
}
