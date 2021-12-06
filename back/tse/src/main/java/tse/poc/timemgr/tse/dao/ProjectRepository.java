package tse.poc.timemgr.tse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tse.poc.timemgr.tse.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
