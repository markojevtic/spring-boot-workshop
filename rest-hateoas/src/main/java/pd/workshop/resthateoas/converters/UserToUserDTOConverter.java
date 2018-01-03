package pd.workshop.resthateoas.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pd.workshop.resthateoas.domain.model.User;
import pd.workshop.resthateoas.web.dto.UserDTO;

@Component
public class UserToUserDTOConverter implements Converter <User, UserDTO> {
    @Override
    public UserDTO convert( User source ) {
        return UserDTO.builder()
                .userId( source.getId() )
                .firstName( source.getFirstName() )
                .lastName( source.getLastName() )
                .build();
    }
}
