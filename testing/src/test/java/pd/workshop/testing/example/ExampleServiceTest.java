package pd.workshop.testing.example;

import java.time.LocalTime;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest
public class ExampleServiceTest {
    @Autowired
    private ExampleService exampleService;

    @Test
    public void getGreetingByTimeReturnsMorningMessageBetweenMidnightAndNoon() {
        final String expectedMorningMessage = "Good morning!";
        final LocalTime morning = LocalTime.of( 6, 1 );

        String result = exampleService.getGreetingByTime( morning );
        assertThat( result ).isEqualTo( expectedMorningMessage );
    }

    @Test
    public void getGreetingByTimeReturnsAfternoonMessageBetweenNoonAndFivePm() {
        //..
    }

    @Test
    public void getGreetingByTimeReturnsEveningMessageBetweenFivePmAndMidnight() {
        //...
    }

    @Test
    public void getGreetingByTimeThrowsExceptionForNullTime() {
        final String expectedErrorMessage = "Time must be a non null value.";
        assertThatThrownBy( () -> exampleService.getGreetingByTime( null ) )
                .isInstanceOf( NullPointerException.class )
                .hasMessage( expectedErrorMessage );
    }
}