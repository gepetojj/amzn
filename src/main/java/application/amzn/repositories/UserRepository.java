package application.amzn.repositories;

import application.amzn.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends CrudRepository<User, Long> {
    UserDetails findByEmail(String email);
}
