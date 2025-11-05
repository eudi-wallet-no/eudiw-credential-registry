package no.idporten.eudiw.credential.registery.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialConfigurationsSupported(
        @JsonProperty("doctype")
        String doctype,
        @JsonProperty("vct")
        String vct,
        @JsonProperty("scope")
        String scope,
        @JsonProperty("format")
        String format,
        @JsonProperty("display")
        List<Display> display,
        @JsonProperty("claims")
        List<Claims> claims,
        @JsonProperty("credential_metadata")
        CredentialMetadata credentialMetadata,
        @JsonProperty("proof_types_supported")
        ProofTypesSupported proofTypesSupported
) {

}
