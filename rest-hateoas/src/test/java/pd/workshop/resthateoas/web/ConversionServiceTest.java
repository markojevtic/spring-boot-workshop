package pd.workshop.resthateoas.web;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.resthateoas.domain.model.User;
import pd.workshop.resthateoas.web.dto.UserDTO;

@RunWith( SpringRunner.class )
@SpringBootTest
public class ConversionServiceTest {

    public static final User MAX_MUSTERMANN = User.builder().id( 1L ).firstName( "Max" ).lastName( "Mustermann" ).build();

    @Autowired
    private ConversionService conversionService;

    /**
     * The test show how to use the conversion service to convert a list in the right way.
     */
    @Test
    public void testConversionOfList() {

        TypeDescriptor userList = TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( User.class ) );
        TypeDescriptor userDtoList = TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( UserDTO.class ) );
        assertThat( conversionService.canConvert( userList, userDtoList ) ).isTrue();

        List <UserDTO> dtoList = (List <UserDTO>) conversionService.convert( Arrays.asList( MAX_MUSTERMANN ), userList, userDtoList );
        assertThat( dtoList ).isNotEmpty();
        assertThat( dtoList.get( 0 ).getUserId() ).isEqualTo( MAX_MUSTERMANN.getId() );
        assertThat( dtoList.get( 0 ).getFirstName() ).isEqualTo( MAX_MUSTERMANN.getFirstName() );
        assertThat( dtoList.get( 0 ).getLastName() ).isEqualTo( MAX_MUSTERMANN.getLastName() );

    }

    /**
     * This test shows most common error for list conversation.
     * It caused by misunderstanding of Java Generics and cheating compiler.
     * To convert an list use example above.
     */
    @Test
    public void testListConverterGenericsGotcha() {
        final List <UserDTO> result = conversionService
                .convert( Arrays.asList( MAX_MUSTERMANN ), (Class <List <UserDTO>>) (Object) List.class );
        assertThat( (Object) result.get( 0 ) ).isInstanceOf( User.class );
    }
}
