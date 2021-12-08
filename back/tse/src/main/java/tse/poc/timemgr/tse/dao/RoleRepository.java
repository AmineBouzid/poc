package tse.poc.timemgr.tse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tse.poc.timemgr.tse.domain.ERole;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
