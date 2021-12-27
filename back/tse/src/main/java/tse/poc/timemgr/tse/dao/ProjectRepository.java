package tse.poc.timemgr.tse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
