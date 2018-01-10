package pd.workshop.resthateoas.web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {

    private String code;
    private String message;
    private List <FieldErrorDTO> fieldErrors;

}
