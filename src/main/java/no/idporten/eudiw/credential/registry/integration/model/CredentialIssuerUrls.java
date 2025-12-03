package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;


import java.net.URL;
import java.util.List;

//@Validated
public record CredentialIssuerUrls (
        @JsonProperty("credential_issuer_urls")
        List<URL> credentialIssuerUrls
){
}
