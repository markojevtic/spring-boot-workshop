package pd.workshop.resthateoas.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import pd.workshop.resthateoas.domain.model.User;

public interface UserService {
    public User getUser(Long id);
    public List<User> filterByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
}
