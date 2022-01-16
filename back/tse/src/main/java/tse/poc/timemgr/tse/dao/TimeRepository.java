package tse.poc.timemgr.tse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.Time;
import tse.poc.timemgr.tse.domain.User;

import java.util.List;
import java.util.Optional;

public interface TimeRepository extends JpaRepository<Time, Long> {

    Optional<List<Time>> findByUser(User user);
    Optional<List<Time>> findByProject(Project project);


}
