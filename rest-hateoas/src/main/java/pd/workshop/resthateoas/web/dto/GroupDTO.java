package pd.workshop.resthateoas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO extends ResourceSupport {
    private String name;
    private UserDTO groupOwner;
    private String other;
}
