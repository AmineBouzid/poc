package tse.poc.timemgr.tse.service;

import org.springframework.http.ResponseEntity;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.payload.request.ProjectRequest;

import java.util.Collection;

public interface ProjectService {

    Collection<Project> findAllProject();

    ResponseEntity<?> deleteProject(Long id);

    ResponseEntity<?> addProject( ProjectRequest projectRequest);
}
