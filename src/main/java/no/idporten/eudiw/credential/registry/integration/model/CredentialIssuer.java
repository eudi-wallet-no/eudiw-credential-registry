package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialIssuer(
        @JsonProperty("credential_issuer")
        @NotBlank
        String credentialIssuer,
        @Valid
        @JsonProperty("credential_configurations_supported")
        Map<String, @Valid CredentialConfiguration> credentialConfiguration
) {
}





