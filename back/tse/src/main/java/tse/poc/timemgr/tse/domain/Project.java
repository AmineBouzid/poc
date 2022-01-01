package tse.poc.timemgr.tse.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(	name = "projects")
public class Project {

    public Long getId_project() {
        return id_project;
    }

    public void setId_project(Long id_project) {
        this.id_project = id_project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_project;

    private String project_name;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Project(String project_name, User manager) {
        this.project_name = project_name;
        this.manager = manager;
    }

    public Project() {

    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
