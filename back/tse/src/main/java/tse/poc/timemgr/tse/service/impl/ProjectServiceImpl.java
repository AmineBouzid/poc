package tse.poc.timemgr.tse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tse.poc.timemgr.tse.dao.TimeRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.Time;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.ProjectRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;
import tse.poc.timemgr.tse.service.ProjectService;
import tse.poc.timemgr.tse.dao.ProjectRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TimeRepository timeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Project> findAllProject(){ return this.projectRepository.findAll(); }

    @Override
    public ResponseEntity<?> deleteProject(Long id) {
        Optional<Project> projectToDelete = this.projectRepository.findById(id);
        if (!projectToDelete.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Project doesnt exist!"));
        }else{
            Optional<List<Time>> timesToDelete = this.timeRepository.findByProject(projectToDelete.get());
            if(timesToDelete.isPresent()){
                for (Time item : timesToDelete.get()){
                    timeRepository.deleteById(item.getId_time());
                }
            }
            projectRepository.delete(projectToDelete.get());
            return ResponseEntity.ok(new MessageResponse("Project deleted successfully!"));
        }
    }

    @Override
    public ResponseEntity<?> addProject(ProjectRequest projectRequest) {
        Optional<User> manager_to_attach = userRepository.findById(projectRequest.getManager_id());

        if (!manager_to_attach.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Manager doesnt exist!"));
        }else {
            Project project = new Project();
            project.setProject_name(projectRequest.getProject_name());
            project.setManager(manager_to_attach.get());
            projectRepository.save(project);
            return ResponseEntity.ok(new MessageResponse("Project added successfully!"));}

    }
}

