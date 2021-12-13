package tse.poc.timemgr.tse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.service.ProjectService;
import tse.poc.timemgr.tse.dao.ProjectRepository;

import java.util.Collection;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Project> findAllProject(){ return this.projectRepository.findAll(); }

    @Override
    @Transactional
    public void addProject(Project project) {
        this.projectRepository.save(project);
    }
}