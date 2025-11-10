package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import no.idporten.eudiw.credential.registry.output.model.OutputCredentials;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialIssuer(
        @JsonProperty("credential_issuer")
        @NotBlank
        String credentialIssuer,
        @NotBlank
        @JsonProperty("credential_configurations_supported")
        Map<String, CredentialConfiguration> credentialConfiguration
) {


    public OutputCredentials getCredentialIssuerForNewFormat() {
        return new OutputCredentials(this);
    }

}





