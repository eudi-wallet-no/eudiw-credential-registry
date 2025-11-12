package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Claims(
        @NotNull
        @JsonProperty("path")
        List<@NotBlank String> path,
        @NotBlank
        @JsonProperty("display")
        List<Display> display
) {
}
