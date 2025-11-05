package no.idporten.eudiw.credential.registry.configuration;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.List;

@ConfigurationProperties("credential-register-properties")
public record ConfigProperties(
        @NotNull List<URI> uri

) {

}