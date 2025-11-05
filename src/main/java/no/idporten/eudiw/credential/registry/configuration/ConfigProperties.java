package no.idporten.eudiw.credential.registry.configuration;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

import java.net.URI;

@ConfigurationProperties("credential-register-properties")
public record ConfigProperties(
        @NotNull URI uri
) {

}