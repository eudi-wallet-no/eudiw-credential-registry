package no.idporten.eudiw.credential.registry.api;

import no.idporten.eudiw.credential.registry.response.CredentialRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import no.idporten.eudiw.credential.registry.response.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Controller
public class CredentialsController {

    @Autowired
    CredentialRegisterService credentialRegisterService;


    @GetMapping(value= "/v1/credentials", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Credentials credentials() {
        return credentialRegisterService.setResponse();
    }
}
