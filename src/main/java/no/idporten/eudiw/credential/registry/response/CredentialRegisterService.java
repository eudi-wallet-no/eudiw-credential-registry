package no.idporten.eudiw.credential.registry.response;

import no.idporten.eudiw.credential.registry.configuration.ConfigProperties;
import no.idporten.eudiw.credential.registry.configuration.CredentialRegisterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialRegisterService {
    private final ConfigProperties configProperties;
    private final CredentialRegisterConfiguration configuration;

    @Autowired
    public CredentialRegisterService(final ConfigProperties configProperties, final CredentialRegisterConfiguration configuration) {
        this.configProperties = configProperties;
        this.configuration = configuration;
    }




}
