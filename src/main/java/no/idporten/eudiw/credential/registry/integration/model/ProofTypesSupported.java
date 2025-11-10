package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown=true)
public record ProofTypesSupported(
        @NotBlank
        @JsonProperty("jwt")
        ProofSigningAlgValuesSupported jwt
) {
}
