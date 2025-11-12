package no.idporten.eudiw.credential.registry.response;


import no.idporten.eudiw.credential.registry.integration.CredentialIssuerMetadataRetriever;
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
                List<Claims> listOfClaims = new ArrayList<>();
                List<Display> innerListOfDisplay = new ArrayList<>();
                List<Display> outerListOfDisplay = new ArrayList<>();
                key.getValue().credentialMetadata().display().stream().forEach((display) -> {
                    Display displayLocal = new Display(display.name(), display.locale());
                    outerListOfDisplay.add(displayLocal);
                });
                key.getValue().credentialMetadata().claims().stream().forEach((claim) -> {
                    claim.display().stream().forEach((display) -> {
                        Display displayLocal = new Display(display.name(), display.locale());
                        innerListOfDisplay.add(displayLocal);
                    });
                    Claims claims = new Claims(claim.path(), innerListOfDisplay);
                    listOfClaims.add(claims);
                });
                String doctype = documentType(key.getValue().vct());
                CredentialMetadata credentialMetadata = new CredentialMetadata(outerListOfDisplay, listOfClaims);
                CredentialsIssuer treatedIssuer = new CredentialsIssuer(issuer.credentialIssuer(), key.getKey(), doctype, key.getValue().format(), credentialMetadata);
                outputCredentials.add(treatedIssuer);
            });
        });
        credentials = new Credentials(outputCredentials);
    }
    public Credentials  getCredentials() {
        return credentials;
    }

    public String documentType(String documentType) {
        if (documentType != null) {
            return "vct";
        } else {
            return "doctype";
        }
    }
}
