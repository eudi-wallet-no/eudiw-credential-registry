package no.idporten.eudiw.credential.registery.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public record ProofTypesSupported(
        @JsonProperty("jwt")
        ProofSigningAlgValuesSupported jwt
) {
}
