package pd.workshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pd.workshop.service.ExampleService;
import pd.workshop.service.ExternalService;

@Service
public class ExampleServiceImpl implements ExampleService {

    public static String DEFAULT_RESULT = "Hello World";

    @Autowired
    private ExternalService externalService;

    @Override
    public String execute() {
        String result = externalService.callExternal();
        if( StringUtils.isEmpty( result ) ) {
            result = DEFAULT_RESULT;
        }
        return result;
    }
}
