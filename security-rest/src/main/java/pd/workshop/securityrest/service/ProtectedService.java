package pd.workshop.securityrest.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The aim of this service is to demonstrate how to apply security level,
 * regarding to it, we keep code as simple as possible, therefore service doesn't implement any interface.
 * In real life you must use interfaces to public you API.
 */
@Service
public class ProtectedService {

    @Secured( { "ROLE_ADMIN" } )
    public String secureMethod() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hi " + userName;
    }

}
