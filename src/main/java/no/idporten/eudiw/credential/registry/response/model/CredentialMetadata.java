package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialMetadata {

    private List<Display> display;
    private List<Claims> claims;

    public CredentialMetadata(List<Display> inputDisplay, List<Claims>  inputClaims) {
        this.display = inputDisplay;
        this.claims = inputClaims;
    }

    public List<Display> getDisplay() {
        return display;
    }
    public List<Claims> getClaims() {
        return claims;
    }


}
