package xyz.codingdaddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.codingdaddy.domain.User;

/**
 * {@link User} database repository
 *
 * @author serhiy
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}