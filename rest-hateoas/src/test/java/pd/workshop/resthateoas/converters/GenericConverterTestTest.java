package pd.workshop.resthateoas.converters;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.resthateoas.domain.model.Group;
import pd.workshop.resthateoas.domain.model.User;
import pd.workshop.resthateoas.web.dto.GroupDTO;

@RunWith( SpringRunner.class )
@SpringBootTest
public class GenericConverterTestTest {
    @Autowired
    private ConversionService conversionService;

    @Test
    public void genericConverterCanConvertGroup() {
        Group group = Group.builder()
                .id( 1L )
                .name( "Test group" )
                .other( "Should not be converted" )
                .groupOwner( User.builder().id( 13L ).firstName( "Max" ).lastName( "Mustermann" ).build() )
                .build();
        GroupDTO groupDTO = conversionService.convert( group, GroupDTO.class );
        assertThat(groupDTO).isNotNull();
        assertThat(groupDTO.getName()).isEqualTo( group.getName() );
        assertThat( groupDTO.getGroupOwner() ).isNotNull();
        assertThat( groupDTO.getGroupOwner().getFirstName() )
                .isEqualTo( group.getGroupOwner().getFirstName() );
        assertThat( groupDTO.getOther() ).isNull();

    }
}