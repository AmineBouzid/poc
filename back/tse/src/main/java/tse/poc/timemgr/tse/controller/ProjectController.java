package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.dao.ProjectRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.ProjectRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;
import tse.poc.timemgr.tse.service.ProjectService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping(path = "/all", produces ="application/json")
    public Collection<Project> findAllProject() {
        return projectService.findAllProject();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @DeleteMapping(path ="/project/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){

        Optional<Project> projectToDelete = this.projectRepository.findById(id);
        if (!projectToDelete.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Project doesnt exist!"));
        }else{
            projectRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Project deleted successfully!"));
        }
    }



    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping(path ="/add")
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectRequest projectRequest)
    {
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
