package tse.poc.timemgr.tse.service;

import tse.poc.timemgr.tse.domain.Project;

import java.util.Collection;

public interface ProjectService {

    Collection<Project> findAllProject();
}
