package pd.workshop.resthateoas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends ResourceSupport {
    @Id
    private Long userId;
    private String firstName;
    private String lastName;
}
