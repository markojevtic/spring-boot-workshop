package pd.workshop.resthateoas.service.impl;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pd.workshop.resthateoas.domain.model.User;
import pd.workshop.resthateoas.service.UserService;

@Service class UserServiceImpl implements UserService {
    @Override
    public User getUser( Long id ) {
        //dummy implementation.
        return null;
    }

    @Override
    public List <User> filterByFirstNameAndLastName( String firstName, String lastName, Pageable pageable ) {
        //dummy implementation.
        return null;
    }
}
