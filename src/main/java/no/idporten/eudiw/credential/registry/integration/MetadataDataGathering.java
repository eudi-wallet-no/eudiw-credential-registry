package no.idporten.eudiw.credential.registry.integration;

import no.idporten.eudiw.credential.registry.configuration.ConfigProperties;
import no.idporten.eudiw.credential.registry.configuration.CredentialRegisterConfiguration;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for sending get-request to well knwon openid credential issuer endpoints of all issuers registered
 * in application.yaml, and formating the data into an object.
 *
 */

@Service
public class MetadataDataGathering {
    @Autowired
    private ConfigProperties configProperties;
    private CredentialRegisterConfiguration configuration;
    private Map<String, CredentialIssuer> mapOfIssuers;

    public MetadataDataGathering() {
        configuration = new CredentialRegisterConfiguration();
        this.mapOfIssuers = new HashMap<>();
    }

    public CredentialIssuer fetchCredentialIssuerFromMetadataRequest(URI uri) {
        uri = URI.create(uri + "/.well-known/openid-credential-issuer");
        CredentialIssuer credentialIssuer = configuration.restClient().get()
                .uri(uri)
                .retrieve()
                .body(CredentialIssuer.class);
        return credentialIssuer;
    }

    public Map<String, CredentialIssuer> getMapOfIssuers() {
        return mapOfIssuers;
    }


    public void loopThroughAllIssuersAndStartFlow()  {
        for (URI uri : configProperties.uri()) {
            CredentialIssuer content = fetchCredentialIssuerFromMetadataRequest(uri);
            mapOfIssuers.put(content.credentialIssuer(), content);
        }
    }
}
