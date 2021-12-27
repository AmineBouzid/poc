package tse.poc.timemgr.tse.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SingleUserRequest {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
