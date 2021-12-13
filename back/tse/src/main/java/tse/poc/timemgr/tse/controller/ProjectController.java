package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.service.ProjectService;

import java.util.Collection;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(path = "/projects", produces ="application/json")
    public Collection<Project> findAllProject() {
        return projectService.findAllProject();
    }
}
