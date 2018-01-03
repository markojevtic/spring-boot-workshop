package pd.workshop.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pd.workshop.service.ExternalService;

@RunWith( SpringRunner.class )
@SpringBootTest
public class ExampleServiceImplTest {

    @MockBean
    private ExternalService externalService;

    @Autowired
    private ExampleServiceImpl exampleService;

    @Test
    public void theExampleReturnsDefaultMessageWhenExternalServiceReturnsNull() {
        given( externalService.callExternal() ).willReturn( null );

        String result = exampleService.execute();
        assertThat( result ).isEqualTo( ExampleServiceImpl.DEFAULT_RESULT );
    }

}