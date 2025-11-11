package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credentials {

    private List<CredentialsIssuer> credentials;


    public Credentials(List<CredentialsIssuer> listOfIssuer) {
        this.credentials = listOfIssuer;

    }

    public List<CredentialsIssuer> getCredentials() {
        return credentials;
    }

}
