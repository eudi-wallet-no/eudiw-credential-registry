package no.idporten.eudiw.credential.registry.response;


import no.idporten.eudiw.credential.registry.integration.CredentialIssuerMetadataRetriever;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;
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
        this.credentials = setResponse();
    }

    public Credentials setResponse() {
        List<CredentialsIssuer> outputCredentials = new ArrayList<>();
        for(CredentialIssuer issuer : credentialIssuerMetadataRetriever.getListOfIssuer()) {
            for (String credentialType : issuer.credentialConfiguration().keySet()) {
                List<Claims> listOfClaims = new ArrayList<>();
                List<Display> innerListOfDisplay = new ArrayList<>();
                List<Display> outerListOfDisplay = new ArrayList<>();
                for (no.idporten.eudiw.credential.registry.integration.model.Display display : issuer.credentialConfiguration().get(credentialType).credentialMetadata().display()) {
                    Display displayLocal = new Display(display.name(), display.locale());
                    outerListOfDisplay.add(displayLocal);
                }
                for (no.idporten.eudiw.credential.registry.integration.model.Claims claim : issuer.credentialConfiguration().get(credentialType).credentialMetadata().claims()) {

                    for (no.idporten.eudiw.credential.registry.integration.model.Display display : claim.display()) {
                        Display displayLocal = new Display(display.name(), display.locale());
                        innerListOfDisplay.add(displayLocal);
                    }

                    Claims claims = new Claims(claim.path(), innerListOfDisplay);
                    listOfClaims.add(claims);

                }
                String doctype;
                if(issuer.credentialConfiguration().get(credentialType).vct()!= null)
                {
                    doctype = issuer.credentialConfiguration().get(credentialType).vct();
                } else {
                    doctype = issuer.credentialConfiguration().get(credentialType).doctype();
                }
                CredentialMetadata credentialMetadata = new CredentialMetadata(outerListOfDisplay, listOfClaims);
                CredentialsIssuer treatedIssuer = new CredentialsIssuer(issuer.credentialIssuer(), credentialType, doctype, issuer.credentialConfiguration().get(credentialType).format(), credentialMetadata);
                outputCredentials.add(treatedIssuer);
            }
        }
        return new Credentials(outputCredentials);
    }
}
