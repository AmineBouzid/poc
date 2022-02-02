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


    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(path = "/all", produces ="application/json")
    public Collection<Project> findAllProject() {
        return projectService.findAllProject();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @DeleteMapping(path ="/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        ResponseEntity<?> responseEntity = projectService.deleteProject(id);
        return responseEntity;
    }


    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping(path ="/add")
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectRequest projectRequest)
    {
        return projectService.addProject(projectRequest);
    }


}
