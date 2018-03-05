package pd.workshop.testing.example;

import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.testing.external.InfoService;

@RunWith( SpringRunner.class )
@SpringBootTest
public class MessageServiceTest {
    private static final String TEST_NUMBER = "064555333";
    private static final String TEST_MESSAGE = "An informative message";
    @Autowired
    private MessageService messageService;

    @MockBean
    private InfoService infoService;

    @Test
    public void messageHasNotBeenSentForNotSubscribedNumber() {
        doReturn( false )
                .when( infoService )
                .hasSubscription( anyString() );

        assertThat( messageService.sendInfo( TEST_NUMBER, TEST_MESSAGE ) )
                .isFalse();
    }

    @Test
    public void messageHasBeenSentForSubscribedNumber() {
        doReturn( true )
                .when( infoService )
                .hasSubscription( anyString() );

        assertThat( messageService.sendInfo( TEST_NUMBER, TEST_MESSAGE ) )
                .isTrue();

        ArgumentCaptor <String> messageCaptor = ArgumentCaptor.forClass( String.class );

        verify( infoService, times( 1 ) )
                .sendMessage( anyString(), messageCaptor.capture() );

        assertThat( messageCaptor.getValue() )
                .startsWith( "Info: " )
                .endsWith( TEST_MESSAGE );
    }
}