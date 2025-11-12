package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Claims {

    private List<String> path;
    private List<Display> display;


    public Claims(List<String> path, List<Display> display) {
        this.path = path;
        this.display = display;
    }

    public List<String> getPath() {
        return path;
    }

    public List<Display> getDisplay() {
        return display;
    }
}
