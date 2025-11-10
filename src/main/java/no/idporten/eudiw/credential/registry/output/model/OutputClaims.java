package no.idporten.eudiw.credential.registry.output.model;

import java.util.List;

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
