package pd.workshop.testing.example;

import java.time.LocalTime;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    public final static String MORNING_GREETING = "Good morning!";
    public final static String AFTERNOON_GREETING = "Good afternoon!";
    public final static String EVENING_GREETING = "Good evening!";

    public String getGreetingByTime(LocalTime time) {
        Objects.requireNonNull( time, "Time must be a non null value." );

        int hour = time.getHour();
        String greeting;

        if(hour >= 0 && hour < 12) {
            greeting = MORNING_GREETING;
        } else if( hour >= 12 && hour < 17){
            greeting = AFTERNOON_GREETING;
        } else {
            greeting = EVENING_GREETING;
        }

        return greeting;
    }
}
