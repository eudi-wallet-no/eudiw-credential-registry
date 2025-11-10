package no.idporten.eudiw.credential.registry.output.model;


import no.idporten.eudiw.credential.registry.integration.model.CredentialConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputCredentials {

    private List<OutputCredentialsIssuer> credentialsIssuer;


    public OutputCredentials(String issuer, Map<String, CredentialConfiguration> configurations) {
        credentialsIssuer = setOutputCredentialIssuer(issuer, configurations);
    }

    private List<OutputCredentialsIssuer> setOutputCredentialIssuer(String issuer, Map<String, CredentialConfiguration> configurations) {
        List<OutputCredentialsIssuer> outputCredentialIssuers = new ArrayList<>();
        for(String key : configurations.keySet())
        {
            String type;
            if (configurations.containsKey(key) && configurations.get(key).doctype()!=null
            ) {
                type =  configurations.get(key).doctype();
            }else {
                type = configurations.get(key).vct();
            }
            OutputCredentialsIssuer outputIssuer = new OutputCredentialsIssuer(issuer, key, type, configurations.get(key).format(), new OutputCredentialMetadata(configurations.get(key).credentialMetadata().display(), configurations.get(key).credentialMetadata().claims()));
            outputCredentialIssuers.add(outputIssuer);
        }
        return outputCredentialIssuers;
    }

    public List<OutputCredentialsIssuer> getOutputCredentialIssuers(){
        return credentialsIssuer;
    }
}
