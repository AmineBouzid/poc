package tse.poc.timemgr.tse.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_project;

    private String project_name;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<User> users;




}
