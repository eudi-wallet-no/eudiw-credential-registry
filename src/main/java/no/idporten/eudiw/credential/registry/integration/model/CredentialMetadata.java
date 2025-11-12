package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialMetadata(
        @NotNull
        @JsonProperty("display")
        List<Display> display,
        @NotNull
        @JsonProperty("claims")
        List<Claims> claims
) {
}
