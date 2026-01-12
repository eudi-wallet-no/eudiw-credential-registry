package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CredentialDefinition {

    @JsonProperty("type")
    private List<String> type;
    @JsonProperty("@context")
    private List<URI> context = new ArrayList<>();

    public CredentialDefinition() {
    }

    public void setType(List<String> type) {
        if (Objects.equals(type.getFirst(), "VerifiableCredential")
        && Objects.nonNull(type.get(1))) {
            this.type = type;
        } else {
            this.type = new ArrayList<>();
        }
    }

    public List<String> getType() {
        return type;
    }

    public void setContext(List<URI> context) {
        if (!(context == null)) {
            if (Objects.equals(context.getFirst(), URI.create("https://www.w3.org/2018/credentials/v1"))
            && Objects.nonNull(context.get(1))) {
                this.context = context;
            } else {
                this.context = new ArrayList<>();
            }
        } else {
            this.context = new ArrayList<>();
        }
    }

    public List<URI> getContext() {
        return this.context;
    }
}
