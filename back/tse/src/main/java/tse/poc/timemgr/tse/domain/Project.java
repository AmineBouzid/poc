package tse.poc.timemgr.tse.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_project;

    private String project_name;

    @ManyToOne
    @JoinColumn(name = "manager")
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
