package pd.workshop.neo4j.repository;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest
public class MasterRepositoryTest {

    @Autowired
    private MasterRepository masterRepository;

    @Before
    public void resetDb() {
        masterRepository.deleteAll();
    }

    @Test
    public void crudWorks() {
        Detail dt1 = Detail.builder().name( "detail1" ).build();
        Detail dt2 = Detail.builder().name( "detail2" ).build();


        Master master = Master.builder()
                .name( "Master-1" )
                .build();
        master.setRelations( Arrays.asList(
                Relation.builder()
                        .detail( dt1 )
                        .master( master )
                        .name( "first" )
                        .build(),
                Relation.builder()
                        .detail( dt2 )
                        .master( master )
                        .name( "second" )
                        .build() ) );

        masterRepository.save( master );

        assertThat( masterRepository.findAll() ).hasSize( 1 );
    }
}