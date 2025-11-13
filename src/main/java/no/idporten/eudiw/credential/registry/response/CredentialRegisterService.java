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
        List<CredentialsIssuer> outputCredentials =
        credentialIssuerMetadataRetriever.getListOfIssuer().stream().flatMap((issuer) ->
            issuer.credentialConfiguration().entrySet().stream().map((key) ->
                 inputDataToResponseIssuer(issuer.credentialIssuer(), key.getKey(), key.getValue(), key.getValue().credentialMetadata())
            )
        ).toList();
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
