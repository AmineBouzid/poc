package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.dao.ProjectRepository;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.service.ProjectService;
import tse.poc.timemgr.tse.service.UserService;

import java.util.Collection;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping(path = "/all", produces ="application/json")
    public Collection<Project> findAllProject() {
        return projectService.findAllProject();
    }

    @PostMapping(path ="/add")
    public void addProject(@Validated @RequestBody Project project, @RequestParam("manager_id") Long manager_id)
    {
        User manager_to_attach = projectRepository.findByManagerId(manager_id);
        project.setManager(manager_to_attach);
        projectRepository.save(project);
    }
}
