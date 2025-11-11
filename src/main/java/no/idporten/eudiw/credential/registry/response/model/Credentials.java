package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credentials {

    private List<CredentialsIssuer> credentials;
    private List<CredentialIssuer> listOfIssuer;


    public Credentials(List<CredentialIssuer> listOfIssuer) {
        this.listOfIssuer = listOfIssuer;
        credentials = new ArrayList<>();
        credentials = formatOutputCredentials();
    }

    private List<CredentialsIssuer> formatOutputCredentials() {
        List<CredentialsIssuer> outputCredentials = new ArrayList<>();

        for (CredentialIssuer issuer : listOfIssuer) {
            for(String key : issuer.credentialConfiguration().keySet()) {

                String type;
                if (issuer.credentialConfiguration().containsKey(key) && issuer.credentialConfiguration().get(key).doctype()!=null
                ) {
                    type =  issuer.credentialConfiguration().get(key).doctype();
                }else {
                    type = issuer.credentialConfiguration().get(key).vct();
                }
                CredentialsIssuer outputIssuer = new CredentialsIssuer(issuer.credentialIssuer(), key, type, issuer.credentialConfiguration().get(key).format(), new CredentialMetadata(issuer.credentialConfiguration().get(key).credentialMetadata().display(), issuer.credentialConfiguration().get(key).credentialMetadata().claims()));
                outputCredentials.add(outputIssuer);
            }
        }
        return outputCredentials;
    }

    public List<CredentialsIssuer> getCredentials(){
        return credentials;
    }

}
