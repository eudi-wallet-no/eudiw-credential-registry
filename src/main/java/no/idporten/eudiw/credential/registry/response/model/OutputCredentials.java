package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputCredentials {

    private List<OutputCredentialsIssuer> credentials;
    private List<CredentialIssuer> listOfIssuer;


    public OutputCredentials(List<CredentialIssuer> listOfIssuer) {
        this.listOfIssuer = listOfIssuer;
        credentials = new ArrayList<>();
        credentials = formatOutputCredentials();
    }

    private List<OutputCredentialsIssuer> formatOutputCredentials() {
        List<OutputCredentialsIssuer> outputCredentials = new ArrayList<>();

        for (CredentialIssuer issuer : listOfIssuer) {
            for(String key : issuer.credentialConfiguration().keySet()) {

                String type;
                if (issuer.credentialConfiguration().containsKey(key) && issuer.credentialConfiguration().get(key).doctype()!=null
                ) {
                    type =  issuer.credentialConfiguration().get(key).doctype();
                }else {
                    type = issuer.credentialConfiguration().get(key).vct();
                }
                OutputCredentialsIssuer outputIssuer = new OutputCredentialsIssuer(issuer.credentialIssuer(), key, type, issuer.credentialConfiguration().get(key).format(), new OutputCredentialMetadata(issuer.credentialConfiguration().get(key).credentialMetadata().display(), issuer.credentialConfiguration().get(key).credentialMetadata().claims()));
                outputCredentials.add(outputIssuer);
            }
        }
        return outputCredentials;
    }

    public List<OutputCredentialsIssuer> getCredentials(){
        return credentials;
    }

}
