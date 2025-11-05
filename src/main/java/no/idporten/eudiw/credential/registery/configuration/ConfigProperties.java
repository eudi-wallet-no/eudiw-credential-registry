package no.idporten.eudiw.credential.registery.configuration;

import org.codehaus.commons.nullanalysis.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("credential-register-properties")
public record ConfigProperties(
        @NotNull URI uri
) {

}