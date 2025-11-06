package no.idporten.eudiw.credential.registry.configuration;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.List;

@ConfigurationProperties("credential-registry")
public record ConfigProperties(
        @NotNull List<URI> credentialIssuerServers

) {

}