package tse.poc.timemgr.tse.domain;


import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
@Table(	name = "times")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_time;

    private Date date_saisie;

    public Date getDate_travail() {
        return date_travail;
    }

    public void setDate_travail(Date date_travail) {
        this.date_travail = date_travail;
    }

    private Date date_travail;

    private String nb_hours;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne
    @JoinColumn(name = "proj_id")
    private Project project;

    public Time(Date date_saisie, Date date_travail, String nb_hours, User user, Project project) {
        this.date_saisie = date_saisie;
        this.date_travail = date_travail;
        this.nb_hours = nb_hours;
        this.user = user;
        this.project = project;
    }

    public Time() {

    }

    public Long getId_time() {
        return id_time;
    }

    public void setId_time(Long id_time) {
        this.id_time = id_time;
    }

    public Date getDate_saisie() {
        return date_saisie;
    }

    public void setDate_saisie(Date date_saisie) {
        this.date_saisie = date_saisie;
    }

    public String getNb_hours() {
        return nb_hours;
    }

    public void setNb_hours(String nb_hours) {
        this.nb_hours = nb_hours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
