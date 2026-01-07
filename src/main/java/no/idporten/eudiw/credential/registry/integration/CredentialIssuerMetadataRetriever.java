package no.idporten.eudiw.credential.registry.integration;

import jakarta.validation.*;
import no.idporten.eudiw.credential.registry.configuration.ConfigProperties;
import no.idporten.eudiw.credential.registry.configuration.CredentialRegisterConfiguration;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuerUrls;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;


import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Responsible for sending get-request to well knwon openid credential issuer endpoints of all issuers registered
 * in application.yaml, and formating the data into an object.
 *
 */

@Service
public class CredentialIssuerMetadataRetriever {
    private static final Logger log = LoggerFactory.getLogger(CredentialIssuerMetadataRetriever.class);
    private final ConfigProperties configProperties;
    private final CredentialRegisterConfiguration configuration;
    private final Validator validator;
    private final RestClient restClient;
    private static final String CREDENTIAL_ISSUER_CONFIG_ENDPOINT = "/.well-known/openid-credential-issuer";

    private List<CredentialIssuer> listOfIssuer;

    @Autowired
    public CredentialIssuerMetadataRetriever(final ConfigProperties configProperties, final CredentialRegisterConfiguration configuration, Validator validator, RestClient restClient) {
        this.configuration = configuration;
        this.restClient = restClient;
        this.validator = validator;
        this.configProperties = configProperties;
    }

    public URI buildWellKnown(URI uri) {
        try {
            URI wellknown = uri.resolve(CREDENTIAL_ISSUER_CONFIG_ENDPOINT + uri.getPath());
            return wellknown;
        } catch (RuntimeException e) {
            log.error("error when resolving well known URI {} with error {}", uri, e.getMessage(), e);
            return null;
        }
    }

    private CredentialIssuer fetchCredentialIssuerFromMetadataRequest(URI uri) {
        CredentialIssuer credentialIssuer;
        URI wellknown = buildWellKnown(uri);
        log.info("Prepared to fetch data from complete url {}", wellknown);
        try {
            credentialIssuer = restClient.get()
                    .uri(wellknown)
                    .retrieve()
                    .body(CredentialIssuer.class);
        } catch (Exception e) {
            log.error("Error fetching credential issuer from metadata request from issuer {} ", uri, e);
            return null;
        }
        Set<ConstraintViolation<CredentialIssuer>> violations = validator.validate(credentialIssuer);
        if (!violations.isEmpty()) {
            log.error( "Issuer with uri {} has these violations {} and is therefore not included", uri, violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
            return null;
        }
        log.info("Successfully fetched credential issuer from complete url {} and with content {}", uri, credentialIssuer);
        return credentialIssuer;
    }


    public void updateListOfIssuer() throws BadRequestException {
        CredentialIssuerUrls uris = null;
        List<URI> listUOfURI;
        listUOfURI = new ArrayList<>();
        try {
            uris = restClient.get()
                    .retrieve()
                    .body(CredentialIssuerUrls.class);
        }catch (HttpClientErrorException e) {
            log.error("Can not get contact with relying party register, or format of contact is unexpected. Issuer is {} and error is {} ", uris, e.getMessage(), e);
            listOfIssuer = null;
            throw new BadRequestException(e.getMessage());
        }catch (HttpServerErrorException e) {
            log.error("Can not get contact with relying party register, or format of contact is unexpected. Error is {} ", e.getMessage(), e);
            listOfIssuer = null;
        }
        if (uris == null) {
            listOfIssuer = null;
            log.error("Could not read URIs from the relying party register");
            return;
        }
        for (URI issuer : uris.credentialIssuerUrls()) {
            listUOfURI.add(URI.create(issuer.toString()));
        }
        this.listOfIssuer = listUOfURI.stream().map(this::fetchCredentialIssuerFromMetadataRequest).filter(Objects::nonNull).toList();
        listOfIssuer.stream().forEach(issuer -> {log.info("issuer {} registered in list of issuers", issuer.getCredentialIssuer());});
        if (listOfIssuer.isEmpty()) {
            log.info("No issuer found for Well-Known CredentialIssuer");
        }
    }


    public List<CredentialIssuer> getListOfIssuer() {
        return listOfIssuer;
    }
}

