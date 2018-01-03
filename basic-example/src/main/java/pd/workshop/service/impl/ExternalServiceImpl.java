package pd.workshop.service.impl;

import org.springframework.stereotype.Service;
import pd.workshop.service.ExternalService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Override
    public String callExternal() {
        throw new NotImplementedException();
    }
}
