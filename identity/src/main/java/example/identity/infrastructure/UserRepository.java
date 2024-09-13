package example.identity.infrastructure;

import example.common.infrastructure.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, String> {
    @Query("FROM app_user u WHERE u.userName=:username AND u.password=:password")
    Optional<AppUser> findUserByUsernameAndPassword(String username,
                                                    String password);
}