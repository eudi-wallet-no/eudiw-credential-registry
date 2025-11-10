package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialMetadata(
        @NotBlank
        @JsonProperty("display")
        List<Display> display,
        @NotBlank
        @JsonProperty("claims")
        List<Claims> claims
) {
}
