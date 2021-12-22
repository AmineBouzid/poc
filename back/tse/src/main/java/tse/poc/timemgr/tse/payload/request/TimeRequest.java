package tse.poc.timemgr.tse.payload.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TimeRequest {

    @NotNull
    private String date_saisie;

    @NotNull
    private String nb_hours;

    @NotNull
    private Long user_id;

    @NotNull
    private Long project_id;

    public  String getDate_saisie() {
        return date_saisie;
    }

    public void setDate_saisie(String date_saisie) {
        this.date_saisie = date_saisie;
    }

    public String getNb_hours() {
        return nb_hours;
    }

    public void setNb_hours(String nb_hours) {
        this.nb_hours = nb_hours;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

}
