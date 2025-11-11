package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputClaims {

    private List<String> path;
    private List<OutputDisplay> display;


    public OutputClaims(List<String> path, List<OutputDisplay> display) {
        this.path = path;
        this.display = display;
    }

    public List<String> getPath() {
        return path;
    }

    public List<OutputDisplay> getDisplay() {
        return display;
    }
}
