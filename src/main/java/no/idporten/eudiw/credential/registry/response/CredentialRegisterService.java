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
            issuer.credentialConfiguration().keySet().stream().forEach((key) -> {
                List<Claims> listOfClaims = new ArrayList<>();
                List<Display> innerListOfDisplay = new ArrayList<>();
                List<Display> outerListOfDisplay = new ArrayList<>();
                issuer.credentialConfiguration().get(key).credentialMetadata().display().stream().forEach((display) -> {
                    Display displayLocal = new Display(display.name(), display.locale());
                    outerListOfDisplay.add(displayLocal);
                });
                issuer.credentialConfiguration().get(key).credentialMetadata().claims().stream().forEach((claim) -> {
                    claim.display().stream().forEach((display) -> {
                        Display displayLocal = new Display(display.name(), display.locale());
                        innerListOfDisplay.add(displayLocal);
                    });
                    Claims claims = new Claims(claim.path(), innerListOfDisplay);
                    listOfClaims.add(claims);
                });
                String doctype;
                if(issuer.credentialConfiguration().get(key).vct()!= null)
                {
                    doctype = issuer.credentialConfiguration().get(key).vct();
                } else {
                    doctype = issuer.credentialConfiguration().get(key).doctype();
                }
                CredentialMetadata credentialMetadata = new CredentialMetadata(outerListOfDisplay, listOfClaims);
                CredentialsIssuer treatedIssuer = new CredentialsIssuer(issuer.credentialIssuer(), key, doctype, issuer.credentialConfiguration().get(key).format(), credentialMetadata);
                outputCredentials.add(treatedIssuer);
            });
        });
        credentials = new Credentials(outputCredentials);
    }
    public Credentials  getCredentials() {
        return credentials;
    }
}
