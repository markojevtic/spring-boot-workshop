package pd.workshop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.domain.model.LockDemo;

@RunWith( SpringRunner.class )
@SpringBootTest
public class LockDemoRepositoryTest {
    @Autowired
    private LockDemoRepository lockDemoRepository;

    @Test
    public void testLock() {
        lockDemoRepository.deleteAll();
        final Long testId = 1L;
        LockDemo lockDemo = LockDemo.builder().id( testId ).text( "First version." ).build();
        lockDemoRepository.save( lockDemo );

        assertThat( lockDemo.getVersion() ).isEqualTo( 0L );

        lockDemo.setText( "2nd" );
        lockDemoRepository.save( lockDemo );
        assertThat( lockDemo.getVersion() ).isEqualTo( 1L );

        assertThat( lockDemoRepository.updateText( testId, "Field update" ) ).isEqualTo( 1 );

        lockDemo = lockDemoRepository.findOne( testId );
        assertThat( lockDemo.getVersion() ).isEqualTo( 2L );
        assertThat( lockDemo.getText() ).isEqualTo( "Field update" );

        //try to cheat
        final LockDemo cheatVersion = lockDemo;
        cheatVersion.setVersion( 1L );
        assertThatThrownBy( () -> lockDemoRepository.save( cheatVersion ) ).isInstanceOf( OptimisticLockingFailureException.class );
    }

}