package tse.poc.timemgr.tse.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_time;

    private Date date_saisie;
    private Long nb_hours;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;


}
