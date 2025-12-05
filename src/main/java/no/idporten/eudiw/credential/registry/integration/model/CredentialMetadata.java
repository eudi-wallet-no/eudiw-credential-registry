package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialMetadata {

    @JsonProperty("display")
    private List<Display> display = new ArrayList<>();
    @JsonProperty("claims")
    private List<Claims> claims = new ArrayList<>();

    public CredentialMetadata(){}

    public void setDisplay(List<Display> display) {
        this.display = display;
    }

    public List<Display> getDisplay() {
        return this.display;
    }

    public void setClaims(List<Claims> claims){
        this.claims = claims;
    }

    public List<Claims> getClaims() {
        return this.claims;
    }


}


