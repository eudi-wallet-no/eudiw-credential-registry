package no.idporten.eudiw.credential.registry.output.model;


import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;

import java.util.ArrayList;
import java.util.List;

public class OutputCredentials {

    private List<OutputCredentialsIssuer> credentialsIssuer;

    public OutputCredentials(CredentialIssuer credentialIssuer){
        credentialsIssuer = new ArrayList<>();
        setOutputCredentialIssuer(credentialIssuer);
    }

    private void setOutputCredentialIssuer(CredentialIssuer credentialIssuer){
        for(String key : credentialIssuer.credentialConfiguration().keySet())
        {
            String type;
            if (credentialIssuer.credentialConfiguration().containsKey(key) && credentialIssuer.credentialConfiguration().get(key).doctype()!=null
            ) {
                type =  credentialIssuer.credentialConfiguration().get(key).doctype();
            }else {
                type = credentialIssuer.credentialConfiguration().get(key).vct();
            }
            OutputCredentialsIssuer outputIssuer = new OutputCredentialsIssuer(credentialIssuer.credentialIssuer(), key, type, credentialIssuer.credentialConfiguration().get(key).format(), new OutputCredentialMetadata(credentialIssuer.credentialConfiguration().get(key).credentialMetadata().display(), credentialIssuer.credentialConfiguration().get(key).credentialMetadata().claims()));
            credentialsIssuer.add(outputIssuer);
        }
    }

    public List<OutputCredentialsIssuer> getOutputCredentialIssuers(){
        return credentialsIssuer;
    }
}
