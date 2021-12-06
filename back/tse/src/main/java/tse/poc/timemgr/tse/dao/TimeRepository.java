package tse.poc.timemgr.tse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tse.poc.timemgr.tse.domain.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
