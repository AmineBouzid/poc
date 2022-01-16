package tse.poc.timemgr.tse.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CrRequest {
    @NotNull
    private Long id;

    @NotNull
    private String date_cr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate_cr() {
        return date_cr;
    }

    public void setDate_cr(String date_cr) {
        this.date_cr = date_cr;
    }
}
