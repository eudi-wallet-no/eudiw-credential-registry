package no.idporten.eudiw.credential.registry.response;


import no.idporten.eudiw.credential.registry.integration.CredentialIssuerMetadataRetriever;
import no.idporten.eudiw.credential.registry.integration.model.CredentialConfiguration;
import no.idporten.eudiw.credential.registry.response.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CredentialRegisterService {

    private final CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever;
    private Credentials credentials;

    @Autowired
    public CredentialRegisterService(CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever) {
        this.credentialIssuerMetadataRetriever = credentialIssuerMetadataRetriever;
        setResponse(credentialIssuerMetadataRetriever);
    }

    public void setResponse(CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever) {
        List<CredentialsIssuer> outputCredentials = new ArrayList<>();
        credentialIssuerMetadataRetriever.getListOfIssuer().stream().forEach((issuer) -> {
            issuer.credentialConfiguration().entrySet().stream().forEach((key) -> {
                CredentialsIssuer credentialsIssuer = inputDataToResponseIssuer(issuer.credentialIssuer(), key.getKey(), key.getValue(), key.getValue().credentialMetadata());

                outputCredentials.add(credentialsIssuer);
            });
        });
        credentials = new Credentials(outputCredentials);
    }

    private CredentialsIssuer inputDataToResponseIssuer(String issuer, String key, CredentialConfiguration credentialConfiguration, no.idporten.eudiw.credential.registry.integration.model.CredentialMetadata credentialMetadata) {
        List<Claims> listOfClaims = new ArrayList<>();
        List<Display> outerListOfDisplay = new ArrayList<>();
        credentialMetadata.display().stream().forEach((display) -> {
            Display displayLocal = new Display(display.name(), display.locale());
            outerListOfDisplay.add(displayLocal);
        });
        credentialMetadata.claims().stream().forEach((claim) -> {
            List<Display> innerListOfDisplay = new ArrayList<>();
            claim.display().stream().forEach((display) -> {
                Display displayLocal = new Display(display.name(), display.locale());
                innerListOfDisplay.add(displayLocal);
            });
            Claims claims = new Claims(claim.path(), innerListOfDisplay);
            listOfClaims.add(claims);
        });
        CredentialMetadata newCredentialMetadata = new CredentialMetadata(outerListOfDisplay, listOfClaims);
        return new CredentialsIssuer(issuer, key, credentialConfiguration.doctype(), credentialConfiguration.format(), newCredentialMetadata);
    }

    public Credentials  getCredentials() {
        return credentials;
    }
}
