package tse.poc.timemgr.tse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tse.poc.timemgr.tse.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
