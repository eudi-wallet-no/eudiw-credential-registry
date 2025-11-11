package no.idporten.eudiw.credential.registry.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import no.idporten.eudiw.credential.registry.integration.CredentialIssuerMetadataRetriever;
import no.idporten.eudiw.credential.registry.response.model.OutputCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Controller
public class CredentialsController {

    @Autowired
    CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever;


    @GetMapping(value= "/v1/credentials", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OutputCredentials credentials() {
        return credentialIssuerMetadataRetriever.getOutputCredentials();
    }


}
